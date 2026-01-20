package com.example.kafkasample.producer.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<SendResult<String, User>> sendMessage(User user) {
        return kafkaTemplate.send("user", user.getName(), user);
    }
}
