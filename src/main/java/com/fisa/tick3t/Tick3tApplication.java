package com.fisa.tick3t;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Tick3tApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tick3tApplication.class, args);
	}

}
