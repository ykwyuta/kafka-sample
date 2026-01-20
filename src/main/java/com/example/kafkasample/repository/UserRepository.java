package com.example.kafkasample.repository;

import com.example.kafkasample.avro.User;

/**
 * Repository interface for User data access.
 * <p>
 * ユーザーデータの永続化を行うリポジトリのインターフェースです。
 * </p>
 */
public interface UserRepository {
    /**
     * Saves a User entity.
     * <p>
     * ユーザー情報を保存します。
     * </p>
     *
     * @param user The User object to save
     */
    void save(User user);
}
