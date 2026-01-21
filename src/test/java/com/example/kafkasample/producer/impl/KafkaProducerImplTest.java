package com.example.kafkasample.producer.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.example.kafkasample.avro.User;

class KafkaProducerImplTest {

    @Mock
    private KafkaTemplate<String, User> kafkaTemplate;

    @InjectMocks
    private KafkaProducerImpl kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        // Arrange
        User user = User.newBuilder().setName("test").setFavoriteColor("red").setFavoriteNumber(1).build();
        when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.completedFuture(new SendResult<>(null, null)));

        // Act
        kafkaProducer.sendMessage(user);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq("user"), eq(user.getName()), eq(user));
    }
}
