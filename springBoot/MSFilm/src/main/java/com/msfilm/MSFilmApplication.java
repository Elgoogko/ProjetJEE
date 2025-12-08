package com.msfilm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MSFilmApplication {
	public static void main(String[] args) {
		SpringApplication.run(MSFilmApplication.class, args);
	}
}
