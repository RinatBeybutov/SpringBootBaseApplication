package com.example.demo.Redis;

import com.example.demo.DTO.SocketAndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY_USER = "User";
    private static final String KEY_SOCKET = "SocketAndUser";
    
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
        hashOperations.put(KEY_USER, user.getId(), user.getName());
    }

    public void add(final SocketAndUser socketAndUser) {
        hashOperations.put(KEY_SOCKET, socketAndUser.getSocketId(), socketAndUser.getUsername());
    }

    public void deleteUser(final String id) {
        hashOperations.delete(KEY_USER, id);
    }

    @Override
    public void deleteSocket(String id) {
        hashOperations.delete(KEY_SOCKET, id);
    }

    public UserRedis findUser(final String id){
        return (UserRedis) hashOperations.get(KEY_USER, id);
    }

    public SocketAndUser findSocketAndUser(final String id) {
        return (SocketAndUser) hashOperations.get(KEY_SOCKET, id);
    }
    
    public Map<Object, Object> findAllUsers(){
        return hashOperations.entries(KEY_USER);
    }

  
}
