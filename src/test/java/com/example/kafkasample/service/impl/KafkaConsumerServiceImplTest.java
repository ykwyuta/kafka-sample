package com.example.kafkasample.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.repository.UserRepository;

class KafkaConsumerServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private KafkaConsumerServiceImpl consumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess_SavesAllUsers() {
        // Arrange
        User user1 = User.newBuilder()
                .setName(UUID.randomUUID().toString().replace("-", ""))
                .setFavoriteColor("Red")
                .setFavoriteNumber((int) (Math.random() * 100))
                .build();

        User user2 = User.newBuilder()
                .setName(UUID.randomUUID().toString().replace("-", ""))
                .setFavoriteColor("Green")
                .setFavoriteNumber((int) (Math.random() * 100))
                .build();

        List<User> users = Arrays.asList(user1, user2);

        // Act
        consumerService.process(users);

        // Assert
        verify(userRepository, times(1)).save(user1);
        verify(userRepository, times(1)).save(user2);
    }

    @Test
    void testProcess_EmptyList() {
        // Arrange
        List<User> users = Collections.emptyList();

        // Act
        consumerService.process(users);

        // Assert
        verify(userRepository, never()).save(any());
    }
}
