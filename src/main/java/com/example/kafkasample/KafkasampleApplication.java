package com.example.kafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot Application for Kafka Sample Project.
 * <p>
 * このアプリケーションは、Spring Bootを使用してApache Kafkaとの連携を実証するサンプルです。
 * Avroスキーマを使用したメッセージの送受信、バッチ処理、およびH2データベースへの保存を行います。
 * </p>
 */
@SpringBootApplication
@EnableScheduling
public class KafkasampleApplication {

	/**
	 * Main entry point of the Spring Boot application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(KafkasampleApplication.class, args);
	}
}
