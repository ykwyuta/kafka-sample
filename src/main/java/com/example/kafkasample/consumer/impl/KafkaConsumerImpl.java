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

/**
 * Implementation of KafkaConsumer.
 * <p>
 * Kafkaトピックからメッセージをバッチで受信し、処理を行います。
 * 手動でのAcknowledgment（確認応答）をサポートしています。
 * </p>
 */
@Component
public class KafkaConsumerImpl implements KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final KafkaConsumerService kafkaConsumerService;

    /**
     * Constructor for KafkaConsumerImpl.
     *
     * @param kafkaConsumerService Service to delegate the processing of consumed
     *                             messages
     */
    public KafkaConsumerImpl(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    /**
     * Listens to the "user" topic and processes messages in batches.
     * <p>
     * KafkaListenerとして動作し、"user"トピックからメッセージを受信します。
     * 受信したメッセージリストを{@link KafkaConsumerService}に渡して処理し、
     * 完了後にAcknowledgmentを実行してオフセットをコミットします。
     * </p>
     *
     * @param users          List of User objects received from Kafka
     * @param acknowledgment Acknowledgment object to manually commit the offset
     */
    @Override
    @KafkaListener(topics = "user", groupId = "order-processing-group")
    public void consume(List<User> users, Acknowledgment acknowledgment) {
        log.info("バッチ受信しました: {} 件", users.size());
        kafkaConsumerService.process(users);
        acknowledgment.acknowledge();
    }
}
