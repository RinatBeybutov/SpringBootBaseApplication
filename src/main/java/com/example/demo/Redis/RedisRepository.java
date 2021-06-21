package com.example.demo.Redis;

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

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String id);
    
    /**
     * find a movie
     */
    UserRedis findUser(String id);
    
}
