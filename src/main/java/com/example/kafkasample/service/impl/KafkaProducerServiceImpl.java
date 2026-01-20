package com.example.kafkasample.service.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;
import com.example.kafkasample.service.KafkaProducerService;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.random.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Implementation of KafkaProducerService.
 * <p>
 * 定期的にメッセージを生成し、KafkaProducerを通じて送信するサービス実装です。
 * </p>
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaProducer kafkaProducer;

    private final Instant stopTime = Instant.now().plus(Duration.ofMinutes(10));

    /**
     * Constructor for KafkaProducerServiceImpl.
     *
     * @param kafkaProducer Producer to send messages
     */
    public KafkaProducerServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Periodically sends messages to Kafka.
     * <p>
     * 10ミリ秒ごとに実行され、ランダムなデータを持つ20件のUserメッセージを生成して送信します。
     * 全ての送信が完了するのを待機します。
     * 起動から10分経過すると送信を停止します。
     * </p>
     */
    @Override
    @Scheduled(fixedRate = 10)
    public void sendMessage() {
        if (Instant.now().isAfter(stopTime)) {
            log.info("送信終了");
            return;
        }

        List<CompletableFuture<SendResult<String, User>>> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = User.newBuilder()
                    .setName(UUID.randomUUID().toString().replace("-", ""))
                    .setFavoriteColor(generateRandomHexColor())
                    .setFavoriteNumber((int) (Math.random() * 100))
                    .build();
            futures.add(kafkaProducer.sendMessage(user));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    /**
     * Generates a random hex color string.
     *
     * @return A random color code in hex format (e.g., #a1b2c3)
     */
    public String generateRandomHexColor() {
        RandomGenerator random = RandomGenerator.getDefault();
        // 0x000000 から 0xFFFFFF までの数値を生成
        int nextInt = random.nextInt(0xffffff + 1);
        // 6桁の16進数にフォーマット（例: #a1b2c3）
        return String.format("#%06x", nextInt);
    }
}
