package com.cognizant.truyum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

public class MenuItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuItemsApplication.class, args);
	}

}
