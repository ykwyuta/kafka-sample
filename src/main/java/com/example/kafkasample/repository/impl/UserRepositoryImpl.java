package com.example.kafkasample.repository.impl;

import com.example.kafkasample.avro.User;
import com.example.kafkasample.repository.UserMapper;
import com.example.kafkasample.repository.UserRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementation of UserRepository.
 * <p>
 * MyBatisのMapperを使用してデータを保存するUserRepositoryの実装クラスです。
 * </p>
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    /**
     * Constructor for UserRepositoryImpl.
     *
     * @param userMapper MyBatis Mapper for User operations
     */
    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * Saves a User entity using MyBatis Mapper.
     * <p>
     * UserMapperを使用してUser情報をデータベースに挿入します。
     * </p>
     *
     * @param user The User object to save
     */
    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
}
