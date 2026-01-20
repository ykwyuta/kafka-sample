package com.example.kafkasample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.kafkasample.service.KafkaProducerService;

@SpringBootApplication
public class KafkasampleApplication implements CommandLineRunner {

	private final KafkaProducerService kafkaProducerService;

	KafkasampleApplication(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkasampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		kafkaProducerService.sendMessage();
	}

}
