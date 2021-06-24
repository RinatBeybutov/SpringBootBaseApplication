package com.example.demo.Redis;

import com.example.demo.DTO.SocketAndUser;

import java.util.Map;

public interface RedisRepository {

    /**
     * Return all movies
     */
    Map<Object, Object> findAllUsers();

    /**
     * Add key-value pair to Redis.
     */
    void add(UserRedis user);

    void add(SocketAndUser socketAndUser);

    /**
     * Delete a key-value pair in Redis.
     */
    void deleteUser(String id);

    void deleteSocket(String id);
    
    /**
     * find a movie
     */
    UserRedis findUser(String id);
    
}
