package com.example.demo.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "User";
    
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    public static int countActiveUsers=0;
    
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    
    public void add(final UserRedis user) {
        hashOperations.put(KEY, user.getId(), user.getName());
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }
    
    public UserRedis findUser(final String id){
        return (UserRedis) hashOperations.get(KEY, id);
    }
    
    public Map<Object, Object> findAllUsers(){
        return hashOperations.entries(KEY);
    }

  
}
