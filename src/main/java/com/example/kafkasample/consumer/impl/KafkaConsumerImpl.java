package com.example.kafkasample.consumer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.consumer.KafkaConsumer;

public class KafkaConsumerImpl implements KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 2000, multiplier = 2.0), dltStrategy = DltStrategy.FAIL_ON_ERROR, topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(topics = "user-orders", groupId = "order-processing-group")
    public void consume(User user) {
        log.info("受信しました: ID={}, User={}", user.getName());

        // 異常系テスト用
        if (user.getName().length() > 10) {
            throw new IllegalArgumentException("名前が長すぎます。再試行を開始します。");
        }
    }

    @DltHandler
    public void handleDeadLetter(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("致命的エラー: DLTトピック {} にメッセージが転送されました: {}", topic, user);
        // ここでアラート通知やDB保存を行う
    }

}
