package com.example.kafkasample.consumer.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.service.KafkaConsumerService;

class KafkaConsumerImplTest {

    @Mock
    private KafkaConsumerService kafkaConsumerService;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private KafkaConsumerImpl kafkaConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsume() {
        // Arrange
        User user1 = User.newBuilder().setName("test1").setFavoriteColor("red").setFavoriteNumber(1).build();
        User user2 = User.newBuilder().setName("test2").setFavoriteColor("blue").setFavoriteNumber(2).build();
        List<User> users = Arrays.asList(user1, user2);

        // Act
        kafkaConsumer.consume(users, acknowledgment);

        // Assert
        verify(kafkaConsumerService, times(1)).process(users);
        verify(acknowledgment, times(1)).acknowledge();
    }
}
