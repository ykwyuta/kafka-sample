package com.example.kafkasample.service;

/**
 * Service interface for managing Kafka message production.
 * <p>
 * メッセージ送信サービスの処理を定義するインターフェースです。
 * </p>
 */
public interface KafkaProducerService {
    /**
     * Triggers the message sending process.
     * <p>
     * メッセージ生成および送信処理を実行します。
     * </p>
     */
    void sendMessage();
}
