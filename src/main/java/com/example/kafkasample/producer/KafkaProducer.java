package com.example.kafkasample.producer;

import com.example.kafkasample.avro.User;

public interface KafkaProducer {
    void sendMessage(User user);
}
