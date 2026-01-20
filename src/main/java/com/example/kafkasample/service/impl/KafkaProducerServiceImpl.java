package com.example.kafkasample.service.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;
import com.example.kafkasample.service.KafkaProducerService;

import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaProducer kafkaProducer;

    public KafkaProducerServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void sendMessage() {
        User user = User.newBuilder()
                .setName("John Doe")
                .setFavoriteColor("Red")
                .setFavoriteNumber(1)
                .build();
        kafkaProducer.sendMessage(user);
    }
}
