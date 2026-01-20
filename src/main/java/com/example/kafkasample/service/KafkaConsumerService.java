package com.example.kafkasample.service;

import java.util.List;
import com.example.kafkasample.avro.User;

/**
 * Service interface for processing consumed Kafka messages.
 * <p>
 * 受信したメッセージに対するビジネスロジックの実行を定義するインターフェースです。
 * </p>
 */
public interface KafkaConsumerService {
    /**
     * Processes a list of User objects.
     * <p>
     * Userオブジェクトのリストを受け取り、必要な処理（例えばデータベースへの保存など）を行います。
     * </p>
     *
     * @param users The list of User objects to process
     */
    void process(List<User> users);
}
