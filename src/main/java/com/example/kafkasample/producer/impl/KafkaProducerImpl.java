package com.example.kafkasample.producer.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of KafkaProducer.
 * <p>
 * KafkaTemplateを使用して、"user"トピックにメッセージを送信します。
 * </p>
 */
@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    /**
     * Constructor for KafkaProducerImpl.
     *
     * @param kafkaTemplate KafkaTemplate for sending messages
     */
    public KafkaProducerImpl(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a User message to the "user" topic.
     * <p>
     * 指定されたUserオブジェクトをKafkaトピック "user" に送信します。
     * 送信は非同期で行われ、CompletableFutureを返します。
     * </p>
     *
     * @param user The User object to send
     * @return A CompletableFuture containing the SendResult
     */
    @Override
    public CompletableFuture<SendResult<String, User>> sendMessage(User user) {
        return kafkaTemplate.send("user", user.getName(), user);
    }
}
