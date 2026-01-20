package com.example.kafkasample.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.repository.UserRepository;
import com.example.kafkasample.service.KafkaConsumerService;

/**
 * Implementation of KafkaConsumerService.
 * <p>
 * 受信したKafkaメッセージをデータベースに保存するサービス実装です。
 * </p>
 */
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    private final UserRepository userRepository;

    /**
     * Constructor for KafkaConsumerServiceImpl.
     *
     * @param userRepository Repository for saving User data
     */
    public KafkaConsumerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Processes a list of User objects by saving them to the database.
     * <p>
     * 受信した各Userオブジェクトをログ出力し、{@link UserRepository}を使用して保存します。
     * </p>
     *
     * @param users The list of User objects to process
     */
    @Override
    public void process(List<User> users) {
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
