package com.example.kafkasample.consumer;

import java.util.List;

import com.example.kafkasample.avro.User;

import org.springframework.kafka.support.Acknowledgment;

/**
 * Interface for consuming Kafka messages.
 * <p>
 * Kafkaコンシューマーの処理を定義するインターフェースです。
 * </p>
 */
public interface KafkaConsumer {
    /**
     * Consumes a batch of User messages.
     * <p>
     * バッチ受信したUserオブジェクトのリストを処理し、手動でAcknowledgmentを実行します。
     * </p>
     *
     * @param users          List of User objects received from Kafka
     * @param acknowledgment Acknowledgment object to manually commit the offset
     */
    void consume(List<User> users, Acknowledgment acknowledgment);
}
