package com.example.kafkasample.repository.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.repository.UserMapper;
import com.example.kafkasample.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
}
