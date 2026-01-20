package com.example.kafkasample.consumer.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.consumer.KafkaConsumer;
import com.example.kafkasample.service.KafkaConsumerService;
import org.springframework.kafka.support.Acknowledgment;

@Component
public class KafkaConsumerImpl implements KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final KafkaConsumerService kafkaConsumerService;

    public KafkaConsumerImpl(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @KafkaListener(topics = "user", groupId = "order-processing-group")
    public void consume(List<User> users, Acknowledgment acknowledgment) {
        log.info("バッチ受信しました: {} 件", users.size());
        kafkaConsumerService.process(users);
        acknowledgment.acknowledge();
    }
}
