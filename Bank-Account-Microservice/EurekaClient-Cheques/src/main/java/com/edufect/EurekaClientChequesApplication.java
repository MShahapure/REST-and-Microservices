package com.edufect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class EurekaClientChequesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientChequesApplication.class, args);
	}
}
