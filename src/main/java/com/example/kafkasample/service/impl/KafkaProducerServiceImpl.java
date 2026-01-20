package com.example.kafkasample.service.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;
import com.example.kafkasample.service.KafkaProducerService;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.random.RandomGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaProducer kafkaProducer;

    private final Instant stopTime = Instant.now().plus(Duration.ofMinutes(10));

    public KafkaProducerServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    @Scheduled(fixedRate = 10)
    public void sendMessage() {
        if (Instant.now().isAfter(stopTime)) {
            log.info("送信終了");
            return;
        }
        User user = User.newBuilder()
                .setName(UUID.randomUUID().toString().replace("-", ""))
                .setFavoriteColor(generateRandomHexColor())
                .setFavoriteNumber((int) (Math.random() * 100))
                .build();
        kafkaProducer.sendMessage(user);
    }

    public String generateRandomHexColor() {
        RandomGenerator random = RandomGenerator.getDefault();
        // 0x000000 から 0xFFFFFF までの数値を生成
        int nextInt = random.nextInt(0xffffff + 1);
        // 6桁の16進数にフォーマット（例: #a1b2c3）
        return String.format("#%06x", nextInt);
    }
}
