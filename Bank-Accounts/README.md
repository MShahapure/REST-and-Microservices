In a banking system, multiple customer accounts are present. The system supports three types of
transactions - cheques, ATMs and internet banking.

In the system, the following tables are present :

• Accounts representing all the accounts and has the fields accId, custCode, name, email,
  mobile, branchCode, accManager. accId is an auto increment primary key.
  
• Cheques contains all the cheque transactions and has the fields chqTransId, accId,
  chqNumber, partyName, partyAccNum, bankName, bankCode, amount. chqTransId is an
  auto increment primary key.
  
• Atms contains all the ATM transactions and has the fields atmTransId, accId, atmNumber,
  amount. atmTransId is an autoincrement primary key.
  
• Transaction through internet banking are stored in the table NetBankings which has the
  fields netTransId, accId, loginName, partyName, partyAccNum, bankName, bankCode,
  amount. netTransId is an autoincrement primary key.
  
• All the transactions are also recorded in the table Transactions which has the fields - transId,
  accId, type, typeId, typeCode, partyName, amount. transId is an autoincrement primary key.
  
In the above tables, accId refers to the accId of the account. Amount can be + or - with + denoting
Deposit and - representing Withdrawal.

Whenever a cheque/atm/Net Banking transaction is done its entry is also created in the table
Transactions with accId being the accId of the particular transaction. Type denotes the type of
transaction and has the possible values as Cheque, ATM or NetBanking.

Depending on the type of transaction :

• TypeId is chqTransId or atmTransId or netTransId

• TypeCode is chqNumber or atmNumber or loginName depending on the type

• PartyName is the same as partyName in Cheques and NetBankings. In the case of ATM
  transaction it will be Self.
  
Step 1 

The banking system offers the following RESTful web services.

• /accounts- GET, POST

• /accounts/{accId}- GET, PUT (to modify email, mobile, accManager)

• /accounts/transactions - GET

• /accounts/transactions/{accId} - GET

• /cheques- GET

• /cheques/{accId} - GET, POST

• /atms- GET

• /atms/{accId} - GET, POST

• /netbankings- GET

• /netbankings/{accId} - GET, POST

All the GET services will support pagination and also sorting by amount.
The GET service for /accounts/transactions/{accId} will also support a request parameter named txn
which will have the value as deposit, withdrawal or all for Deposits, Withdrawals or All transactions.
Test these services by using POSTMAN. The easiest way to use POSTMAN is to install the POSTMAN
Chrome plugin.

Step 2 

Add the following RESTful services.

• /accounts/custs
  A report of all customers having accId, custCode, count of transactions for the customer and
  the current balance in the account. A sample is given below :
  [
   {
     "accId" : 1,
     "custCode": 478,
	 "totalTransactions" : 2,
	 "currentBalance" : 40000
   }
  ]
  
• /accounts/branches

  A report of all the bank branches as indicated by the branchCode in the table Accounts. This
  report consists of
   o branchCode
   o number of accounts at that branch
   o total balance of all the accounts of that branch
   o the number of transactions of the accounts of that branch

A sample is given below :
[
{
"branchCode" : "BR1003",
"accounts": 12,
"balance" : 356100,
"txncount" : 86
}
]

So,this is the problem statement on which we are going to build REST API.

## Take a look at annotations that are used in code

## @RestController Annotation

Spring controllers use the @Controller annotation. The controllers are annotated with the
@Controller annotation, which allows the classes to be auto-detected using classpath scanning.


A typical use case of @Controller annotation is request handling methods as seen in the example
below:


@Controller

@RequestMapping("employees")

public class EmployeesController {

@GetMapping(value="/{id}", produces = "application/json")

public @ResponseBody Employee getEmployee (@PathVariable long id) {

// ....

}

}

The request handling method is annotated with @ResponseBody. The @ResponseBody annotation
enables automatic serialization or conversion of the return object into HttpResponse.

To simplify creating RESTful web services, the @RestController annotation was introduced. The
@RestController annotation provides the convenience of combining the @Controller and
@ResponseBody annotations. As shown in the example below, this eliminates the need to annotate
every request handling method of the controller class with the @ResponseBody annotation.

@RestController

@RequestMapping("employees ")

public class EmployeesRestController {

@GetMapping(value="/{id}", produces = "application/json")

public Employee getEmployee (@PathVariable int id) {

// ....

}

}

## Mapping Annotations

The commonly used mapping annotations are @RequestMapping, @GetMapping, @PostMapping,
@PutMapping and @DeleteMapping.

The @RequestMapping annotation is used to map web requests to Spring Controller methods. It
maps all types of web requests - GET, POST etc. So, a GET type request can be specified in the
@RequestMapping annotation by adding the parameter request = RequestMethod.GET and similarly
for a POST, PUT and DELETE request.

@GetMapping, @PostMapping, @PutMapping and @DeleteMapping also map web requests to the
Spring Controller methods. However, unlike @RequestMapping they map the specify type of web
request as suggested by the name of the annotation.


The @RequestMapping annotation can be applied at the level of the class or a method in a
controller. The class-level annotation maps a specific request path on the controller. Additional
method-level annotations can be used to define specific mappings with the handler methods.


@RestController

@RequestMapping("employees ")

public class EmployeesRestController {

@GetMapping(value="/{id}", produces = "application/json")

public Employee getEmployee (@PathVariable int id) {

// ....

}

@RequestMapping(value="/bills", produces = "application/json", method=RequestMethod.GET)

public List<Bills> getBills () {

// ....

}

}

In the above example, a class level @RequestMapping annotaton has been defined in
@RequestMapping("employees "). As a result, GET requests to /employees/{id} will be handled by
the method getEmployee, while requests to /employees/bills will be handled by the method getBills.

## Path Variables

Path Variables provide a mechanism to bind parts of the URI to variables by using @PathVariable
annotation.

The example below denotes the usage of a single Path Variable.

@GetMapping (value = "/employees/{id}")

public Employee getEmployeeById(@PathVariable long id) {

// use the id to get the employee details

}


@PathVariable provides the added feature of automatic type conversion. So, in the above example,
the Path Variable is converted into a long.

Multiple Path Variables are also supported as shown in the example below :

@GetMapping (value = "/employees/{empid}/bills/{billid}")

public Bill getBillById(@PathVariable long empid, @PathVariable long billid) {

// ...

}


## @RequestBody

The @RequestBody annotation is used to map the body of the HttpRequest to an object. It enables
the automatic deserialization of the body of the HttpRequest into a Java object.

@PostMapping(value="/employees", produces="application/json")

public Employee newEmployee(@RequestBody Employee newEmp) {

//.....

}

In the above example, Spring automatically deserializes the body of the HttpRequest into an object
of Employee.

• Take a look at JPA Annotations that we have user in this code

In Java Persistence API (JPA), a persistent class is referred to as an entity. An entity is a plain old Java
object (POJO) class that is mapped to the database and configured for usage through JPA using
annotations. The entity class does not need to implement any interfaces or extend any classes. It
must have its identity defined and can have configuration metadata specified with Java annotations.
Some of the commonly used annotations are detailed below.


## @Entity
Used before the class to mark it as an entity.

## @Table
Used to specify the table to which this entity class maps. This annotation is optional and is only
required when the default table name to be assumed by JPA does not match the relational schema.
If not specified, the name of the class is assumed to be the name of the table.

## @Id
Used to specify the primary key of the entity. It is essential for every entity to indicate its primary
key fields. If compound primary key fields are required, they are specified using @IdClass or
@EmbeddedId annotations.

## @GeneratedValue
Used to specify the type of primary key generator that is most appropriate. The strategy parameter
is used to specify the generator type. To use an auto-increment of the column, the strategy used is
IDENTITY, which can be specified as

@GeneratedValue(strategy="GenerationType.IDENTITY")

The @GeneratedValue annotation is optional and if not specified it takes the default strategy as
GenerationType.AUTO

## @Column
Used to specify that the entity's fields are stored in a database table column. Some common
attributes @Column annotation are :

• name : Used to specify an alternative column name, default is the name of the field.

• unique : Used to specify that a column is not allowed to contain duplicate values, default
value is false.

• nullable : Used to specify that a column can contain a null value, default value is true.
These can be used as

@Column(name="emp_name", unique=true, nullable=false)

All the above attributes are optional. Specifying the @Column attribute is also optional.


## @ManyToOne
Used to specify a many-to-one mapping. An entity Order can have multiple orders from the same
Customer. So many orders are mapped to 1 customer. It can be specified in the entity Order by
specifying @JoinColumn with the name of column in the entity Customer.

@ManyToOne
@JoinColumn("custId")
private Customer customer;


## @OneToMany
Used to specify a one-to-many mapping. In the above example, a customer can have multiple
orders. So for 1 customer there are many orders. An entity Customer can have multiple entity of
Order. It can be specified in the entity Customer by mapping it to the field named customer in the
entity Order.

@OneToMany(mappedBy="customer")

private Set<Order> orders;
	
	
## @OneToOne
Used to specify a one-to-one mapping. In the above example, an Order can have one Address. So
there is one-to-one mapping between the entity Order and the entity Address. It can be specified in
the entity Order by mapping it to the field named order in the entity Address.

@OneToOne(mappedBy="order")

private Address address;

In the entity Address, it would be specified in the JoinColumn. As it is a one-to-one mapping, unique
is set to true, nullable is false and updatable is false.

@OneToOne

@JoinColumn("orderId",unique=true, nullable=false, updatable=false)

private Order order;

So, the above entities used would be


@Entity

@Table(name="MyCustomers")

public class Customer {

@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)

private long custId;

@Column(name="cust_name")

private String name;

private String email;

private String mobile;

@OneToMany(mappedBy = "customer")

private Set<Order> orders;

}

This will create a table by the name MyCustomers which will have the columns cust_id, cust_name,
email and mobile.


@Entity

public class Order {

@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)

private long orderId;

private int amount;

private String paymentMode;

@ManyToOne

@JoinColumn(name="custId")

private Customer customer;

@OneToOne (mappedBy="order")

private Address address;

}

This will create a table by the name Order which will have the columns order_id,
amount, payment_mode, cust_id.


@Entity

public class Address {

@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)

private long addressId;

private String firstLine;

private String secondLine;

private String city;

private String zipcode;

@OneToOne

@JoinColumn(name="orderId", unique=true, nullable=false, updatable=false)

private Order order;

}

This will create a table by the name Address which will have the columns
address_id, first_line, second_line, city, zipcode, order_id.


## Spring Data
The objective of Spring Data mission is to provide a Spring-based programming model for data
access while still retaining the special traits of the underlying data store.
Spring Data makes it easy to use data access technologies, relational and non-relational databases,
map-reduce frameworks, and cloud-based data services. It is an umbrella project and contains
several subprojects which are specific to a given database.

The important features of Spring Data are:

• Powerful repository and custom object-mapping abstractions

• Dynamic query derivation from repository method names

• Support for transparent auditing (i.e. created, last changed)

• Ability to integrate custom repository code

• Advanced integration with Spring MVC controllers

## Spring Data JPA
Spring Data JPA is one of the subprojects of Spring Data. Its objective is to make it easy to implement
JPA based repositories. The Java Persistence API (JPA) is a Java specification for accessing, persisting,
and managing data between Java objects / classes and a relational database. JPA is considered as the
standard industry approach for Object to Relational Mapping (ORM) in the Java Industry.
Spring Data JPA provides enhanced support for JPA based data access layers. It makes it easier to
develop Spring applications that use data access technologies.

Prior to Spring Data JPA, implementing a data access layer was cumbersome and required a lot of
code to execute simple queries as well as implement capabilities like pagination and auditing. Spring
Data JPA significantly reduces the amount of code required. The developer needs to specify the
repository interfaces with custom finder methods and Spring provides the implementation
automatically.

Important features of Spring Data JPA include :

• Build repositories based on Spring and JPA

• Support for pagination and custom data access code

• Validation of @Query annotated queries at bootstrap time

• Transparent auditing of domain class

## Query

Spring Data JPA supports defining a query manually or having it being derived from the method
name.

## Query Creation from Method Name

A common way of specifying a query is using method names to create a query.

For e.g. there is an entity User with the fields :name, age and dob. dob is a date and refers to the
date of birth. A UserRepository is defined as shown below:

public interface UserRepository extends Repository<User, Long> {

List<User> findByName(String name);

List<User> findByAge (int age);

List <User> findByNameIn(List<String> names);

List <User> findByNameAndAge (String name, int age);

}

The methods defined in the interface UserRepository are used to create queries using the method
names.

• List<User> findByName(String name) - translates into "SELECT u from User u where u.name
= ?1"

• List<User> findByAge (int age) - translates into "SELECT u from User u where u.age = ?1"

• List <User> findByNameIn(List<String> names) translates into "SELECT u from User u where
u.name IN ?1"

• List <User> findByNameAndAge (String name, int age) translates into "SELECT u from User u
where u.name = ?1 AND u.age = ?2"


## @Query

Queries can be bound to a Java method by using the Spring Data JPA @Query annotation as shown
in the example below.

public interface UserRepository extends JpaRepository<User, Long> {

@Query("SELECT u FROM User u WHERE u.name = ?1")

List<User> queryName (String name);

@Query("SELECT u FROM User u WHERE u.name = ?1 AND u.age > ?2")

List<User> queryNameAgeMore (String name, int age);

@Query("SELECT u FROM User u WHERE u.name LIKE %?1")

List<User> queryNameEnd (String nameEnd);

}

By default, Spring Data JPA uses position-based parameter binding, as described in the above
example. It also supports named parameters by using the @Param annotation to provide a method
parameter a name. This name can then be used in the query as shown in the example below.

@Query("SELECT u FROM User u WHERE u.name = :name1 AND u.age > :age1")

List<User> queryNameAge(@Param("name1") String name, @Param("age1") int age);
	
## Result Classes with Constructor Expressions

Spring Data query methods can have different SELECT options which may not map to the entity
managed by the repository. A common return in this case is an Object array as shown in the example
below.

This class can then be used as follows:

@Query("SELECT u.name, u.age FROM User u")

List<Object[]> queryNameAge();

It will return a list of Object[] where each Object array will have two elements corresponding to
name and age.
An alternative to representing compound results by Object arrays is to use custom result classes and
result constructor expressions.

In the example below, a class UserNameAge has been defined which contains the fields name and
age, which is used in the query.

public class UserNameAge {

private final String name;

private final int age;

UserNameAge (String name, int age) {

this.name = name;

this.age = age;

}

String getName() {

return this.name;

}

String getAge () {

return this.age;

}

}

This class can then be used as follows:

@Query("SELECT new UserNameAge(u.name, u.age) FROM User u")

List<UserNameAge> queryNameAge();

Note that the complete name of the class along with its package name should be used.

