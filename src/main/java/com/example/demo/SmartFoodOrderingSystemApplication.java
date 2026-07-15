package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SmartFoodOrderingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartFoodOrderingSystemApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("Admin@123"));
	}

}
