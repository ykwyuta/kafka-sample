package com.example.kafkasample.producer;

import com.example.kafkasample.avro.User;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

public interface KafkaProducer {
    CompletableFuture<SendResult<String, User>> sendMessage(User user);
}
