package com.example.kafkasample.producer;

import com.example.kafkasample.avro.User;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

/**
 * Interface for producing Kafka messages.
 * <p>
 * Kafkaへのメッセージ送信処理を定義するインターフェースです。
 * </p>
 */
public interface KafkaProducer {
    /**
     * Sends a User message to the Kafka topic.
     * <p>
     * UserオブジェクトをKafkaに非同期で送信します。
     * </p>
     *
     * @param user The User object to send
     * @return A CompletableFuture containing the SendResult
     */
    CompletableFuture<SendResult<String, User>> sendMessage(User user);
}
