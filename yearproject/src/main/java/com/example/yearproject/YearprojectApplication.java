package com.example.yearproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.yearproject", "com.example.yearproject.config"})
public class YearprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(YearprojectApplication.class, args);
	}

}
