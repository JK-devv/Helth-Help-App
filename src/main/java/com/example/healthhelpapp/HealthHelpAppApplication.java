package com.example.healthhelpapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HealthHelpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthHelpAppApplication.class, args);
	}

}
