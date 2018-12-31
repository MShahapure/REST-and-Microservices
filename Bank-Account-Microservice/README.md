Here in this example we are creating Microservice.

# Problem Statement

A bank, has defined a microservices architecture to serve its customers. A customer can open,
manage and view its bank accounts. Also, they can transact at the bank using three different modes -
Cheques, ATM and Net banking.

The bank has organized its services around independent microservices to serve these requirements.
It has created 4 different microservices - accounts, cheques, atms, and netbankings.

## Step 1
Create a Eureka server that will act as the discovery server.

Create a Eureka client which will provide the Accounts microservice.

Create a Feign client which will provide a single gateway to all the services.

The Accounts microservice will have an independent database with two tables :

• AccountsTable representing all the accounts and has the fields accId, custCode, name, email,
mobile, branchCode, accManager. accId is an auto increment primary key.

• TransactionsTable representing all the transactions and has the fields - transId, accId, type,
typeId, typeCode, partyName, amount. transId is an autoincrement primary key. accId will
be a foreign key in Transactions with a one to many mapping.

The Feign client will expose the following services of the Accounts microservice :

• /accounts- GET, POST

• /accounts/{accId}- GET, PUT (to modify email, mobile, accManager)

• /accounts/transactions/{accId} - GET

## Step 2

Create a Eureka client which will provide the Cheques microservice.

The Cheques microservice will have an independent database with a single table :

• ChequesTable contains all the cheque transactions and has the fields chqTransId, accId,
chqNumber, partyName, partyAccNum, bankName, bankCode, amount. chqTransId is an
auto increment primary key.
The Feign client will expose the following services of the Cheques microservice :

• /cheques- GET

• /cheques/{accId} - GET, POST

Note that when a cheque is posted, after step 2 , only an entry is made to the table ChequesTable.
No entry is made in the Accounts database which is a separate database.

## Step 3

For a cheque to be cleared, the account must have sufficient balance. So, the Cheques microservice
needs to get the balance of a particular account from the Accounts microservice.

Hence, in the Accounts microservice define a get balance service which will exposed by the Feign
client at

• /accounts/balance/{accId} - GET

The Cheques microservice will use the above service to enquire about the balance and then take
appropriate action.

The Cheques microservice can call the service as shown in the example below :

1. First define a bean using the RestTemplate.

@Bean

public RestTemplate restTemplate(RestTemplateBuilder builder) {

return builder.build();

}

2. Autowire the bean.

@Autowired

private RestTemplate rest;

3. Then use it call the required service.

String url = "http://localhost:8080/accounts/balance/"+accId;

long balance = rest.getForObject(url,Long.class);

As the Feign client can timeout, because of the sequence of events, in the Feign client add the
dependency Hystrix. In the application.properties of the Feign client, add the following :

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=4000.

## Step 4

If a cheque transaction is successful, the Accounts microservice needs to be informed so that it can
take appropriate action.

The Accounts microservice define a service to post a transaction which will exposed by the Feign
client at

• /accounts/posttx/{accId} - POST

The Cheques microservice will use the above service to inform the Accounts microservice of a
successful cheque transaction.

String url = "http://localhost:8080/accounts/posttx/"+accId;
PostTransaction obj = rest.postForObject(url,pt,PostTransaction.class);

Here, a Java class named PostTransaction has been defined. This class defines the format in which
the Accounts microservice is expecting information. pt in the above code, is an object of type
PostTransaction with the requisite values.

## Step 5

In a similar manner, create the ATMs microservices. The Feign client will expose the following
services of the ATMs microservice :

• /atms - GET

• /atms/{accId} - GET, POST

## Step 6
In a similar manner, create the NetBankings microservices. The Feign client will expose the following
services of the NetBankings microservice :

• /netbankings - GET

• /netbankings/{accId} - GET, POST

## Lets understand about Eureka & Feign Client

## Spring Cloud Netflix

Spring Cloud Netflix provides Netflix OSS integrations for Spring Boot applications using annotations
based configuration. It helps in creating microservices instead of a monolithic application. In this
architecture, service discovery is one of the key tenets. Service discovery is provided using Eureka as
the Netflix Service Discovery Server and Client. For service discovery, an embedded Eureka server is
created with declarative Java configuration. Eureka instances can then be registered and clients can
discover the instances.

## Eureka Server
Eureka server is the registry of all the available services. The different services can register and deregister
themselves on this server. To implement a Eureka Server for using as service registry :

• Add spring-cloud-starter-netflix-eureka-server to the dependencies

• Enable the Eureka Server in the Spring Boot application with the annotation
@EnableEurekaServer.

@SpringBootApplication

@EnableEurekaServer

public class EurekaServerApplication {

  public static void main(String[] args) {

    SpringApplication.run(EurekaServerApplication.class, args);

  }

}

• Configure the properties in the application.yml

eureka:

  instance:

    hostname: localhost

  client:

    serviceUrl:

      defaultZone: http://localhost:8761/eureka/

  registerWithEureka: false

server:

  port: 8761

The Eureka server has been configured for port 8761 which is the default one for Eureka servers. The
attribute 'registerWithEureka:false' is informing the built in Eureka Client not to register with ‘itself’
as this application is acting as a server.

After running the application, the URL http://localhost:8761 can be used to view the Eureka
dashboard and list of all the registered instances.

## Eureka Client
The Eureka Client is designed to be a microservice which registers itself to the discovery server using
the properties defined in the application.yml. To configure a Spring Boot application as a eureka
service and a client for discovery server

• Add spring-cloud-starter-netflix-eureka-client in the dependencies.

• Annotate the Spring Boot application with either @EnableDiscoveryClient or
@EnableEurekaClient

@SpringBootApplication

@EnableEurekaClient

public class EurekaClientApplication {

  public static void main(String[] args) {

   SpringApplication.run(EurekaServerApplication.class, args);

  }

}

• Configure the application.yml

spring:

  application:

    name:spring-client001

eureka:

  client:

    serviceUrl:

      defaultZone: http://localhost:8761/eureka/

  instance:

    preferIpAddress:false

server:

  port: 2222

error:

  whitelabel:

    enabled:false

The spring.application.name is the unique identifier for this service.
The eureka.client.serviceUrl.defaultZone is the URL of the Eureka discovery server.

In some cases, it is preferable for Eureka to advertise the IP addresses of the services rather than the
hostname. Set eureka.instance.preferIpAddress to true and, when the application registers with
eureka, it uses its IP address rather than its hostname.

Setting error.whitelabel.enabled to false, disables the default error page for a Spring Boot
application.

## Feign Client
Feign is a declarative web service client which makes writing web service clients easier. Feign can be
thought of as a discovery-aware Spring RestTemplate using interfaces to communicate with
endpoints. This interfaces are automatically implemented at runtime and instead of service urls use
service names.

To enable a Feign client

• Add spring-cloud-starter-feign package in the dependencies.

• Annotate the Spring Boot application @EnableFeignClients.

@SpringBootApplication

@EnableFeignClients

@RestController

public class MyFeignApplication {

  @Autowired

  private MyFeignClient feignClient;

    public static void main(String[] args) {

      SpringApplication.run(MyFeignApplication.class, args);

}

@GetMapping("/getnums")

public Object getNumber() {

  Object obj = feignClient.getNumber();

  return obj;

}

}

• Create an interface annotated with @FeignClient(“service-name”) which was autowired in
the RestController.

@FeignClient("spring-client001")

public interface MyFeignClient {
  
  @GetMapping(value="/getNumbers")

}

Here, " spring-client001" is the Eureka client which will be used.

• Configure the application.yml


spring:

  application:

    name:spring-myfeign

eureka:
  
  client:

    serviceUrl:

      defaultZone: http://localhost:8761/eureka/

server:

  port: 2401

The eureka.client.serviceUrl.defaultZone is the URL of the Eureka discovery server. A client can
access all the services at localhost:2401 which is the port of the Feign client.
