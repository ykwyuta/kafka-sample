package com.example.kafkasample.repository.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.repository.UserMapper;

class UserRepositoryImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        User user = User.newBuilder().setName("test").setFavoriteColor("red").setFavoriteNumber(1).build();

        // Act
        userRepository.save(user);

        // Assert
        verify(userMapper, times(1)).insert(user);
    }
}
