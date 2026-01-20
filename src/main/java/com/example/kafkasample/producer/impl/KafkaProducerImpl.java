package com.example.kafkasample.producer.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(User user) {
        kafkaTemplate.send("user", user.getName(), user);
    }
}
