package com.travelbooking.main;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;


// http://localhost:8080/swagger-ui.html

@SpringBootApplication
@EntityScan(basePackages = "com.travelbooking.model")
@ComponentScan(basePackages = "com.travelbooking")
@EnableJpaRepositories(basePackages = "com.travelbooking.repository")
public class TravelBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBookingSystemApplication.class, args);
	}

}
