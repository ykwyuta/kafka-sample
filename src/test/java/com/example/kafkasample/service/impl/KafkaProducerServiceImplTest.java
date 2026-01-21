package com.example.kafkasample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.SendResult;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.producer.KafkaProducer;

class KafkaProducerServiceImplTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private KafkaProducerServiceImpl producerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        // Arrange
        when(kafkaProducer.sendMessage(any(User.class)))
                .thenReturn(CompletableFuture.completedFuture(new SendResult<>(null, null)));

        // Act
        producerService.sendMessage();

        // Assert
        // プロデューサーは20回呼ばれるはず
        verify(kafkaProducer, times(20)).sendMessage(any(User.class));
    }

    @Test
    void testGenerateRandomHexColor() {
        String color = producerService.generateRandomHexColor();
        assertNotNull(color);
        assertEquals(7, color.length());
        assertEquals('#', color.charAt(0));
    }
}
