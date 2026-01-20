package com.example.kafkasample.repository;

import com.example.kafkasample.avro.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * MyBatis Mapper for User entity.
 * <p>
 * データベースのusersテーブルに対する操作を定義します。
 * </p>
 */
@Mapper
public interface UserMapper {

    /**
     * Inserts a User record into the database.
     * <p>
     * Userオブジェクトの情報をデータベースに保存します。
     * </p>
     *
     * @param user The User object to insert
     */
    @Insert("INSERT INTO users (name, favorite_number, favorite_color) VALUES (#{name}, #{favoriteNumber}, #{favoriteColor})")
    void insert(User user);
}
