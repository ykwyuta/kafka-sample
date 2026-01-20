package com.example.kafkasample.repository;

import com.example.kafkasample.avro.User;

public interface UserRepository {
    void save(User user);
}
