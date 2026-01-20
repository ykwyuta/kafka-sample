package com.example.kafkasample.service;

import java.util.List;
import com.example.kafkasample.avro.User;

public interface KafkaConsumerService {
    void process(List<User> users);
}
