package com.example.kafkasample.consumer.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.consumer.KafkaConsumer;
import com.example.kafkasample.repository.UserRepository;

@Component
public class KafkaConsumerImpl implements KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final UserRepository userRepository;

    public KafkaConsumerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "user", groupId = "order-processing-group")
    public void consume(List<User> users) {
        log.info("バッチ受信しました: {} 件", users.size());
        users.forEach(user -> {
            log.info("受信しました: ID={}, User={}", user.getName(), user);
            userRepository.save(user);

            // 異常系テスト用
            if (user.getName().length() > 10) {
                // throw new IllegalArgumentException("名前が長すぎます。再試行を開始します。");
            }
        });
    }
}
