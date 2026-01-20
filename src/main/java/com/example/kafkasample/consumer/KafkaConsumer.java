package com.example.kafkasample.consumer;

import java.util.List;

import com.example.kafkasample.avro.User;

public interface KafkaConsumer {
    void consume(List<User> users);
}
