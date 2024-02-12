package com.training.springboot.app.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for this springboot application.
 * Uses @Configuration @EnableAutoConfiguration @ComponentScan with their default attributes.
 * Enables component scanning for interfaces declared as feign clients.
 * 
 * @author Pablo
 *
 */
@EnableFeignClients
@SpringBootApplication
public class SpringbootSimilarProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSimilarProductServiceApplication.class, args);
	}

}
