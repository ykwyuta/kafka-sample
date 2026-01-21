package com.example.kafkasample.repository;

import com.example.kafkasample.avro.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatCode;

@MybatisTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        // Arrange
        User user = User.newBuilder()
                .setName("testuser")
                .setFavoriteColor("#ffffff")
                .setFavoriteNumber(7)
                .build();

        // Act & Assert
        assertThatCode(() -> userMapper.insert(user)).doesNotThrowAnyException();

        // Note: To verify insertion properly, we might need a Select method in Mapper,
        // but currently Mapper only has Insert.
        // We assume no exception means SQL was valid and executed.
    }
}
