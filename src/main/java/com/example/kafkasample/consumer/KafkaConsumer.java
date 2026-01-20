package com.example.kafkasample.consumer;

import java.util.List;

import com.example.kafkasample.avro.User;

import org.springframework.kafka.support.Acknowledgment;

public interface KafkaConsumer {
    void consume(List<User> users, Acknowledgment acknowledgment);
}
