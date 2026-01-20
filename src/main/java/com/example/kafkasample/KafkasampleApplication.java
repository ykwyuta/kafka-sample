package com.example.kafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkasampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkasampleApplication.class, args);
	}
}
