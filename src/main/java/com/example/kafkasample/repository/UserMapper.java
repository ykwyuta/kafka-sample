package com.example.kafkasample.repository;

import com.example.kafkasample.avro.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (name, favorite_number, favorite_color) VALUES (#{name}, #{favoriteNumber}, #{favoriteColor})")
    void insert(User user);
}
