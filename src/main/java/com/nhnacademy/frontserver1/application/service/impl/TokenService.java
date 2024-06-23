package com.nhnacademy.frontserver1.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String TOKEN_HASH_NAME = "Token:";
    private final long TOKEN_EXPIRATION_TIME = 1800; // 30 minutes in seconds

    public String storeToken(String token) {
        String key = generateUuidKey();
        redisTemplate.opsForHash().put(TOKEN_HASH_NAME, key, token);
        redisTemplate.expire(TOKEN_HASH_NAME, TOKEN_EXPIRATION_TIME, TimeUnit.SECONDS);
        return key;
    }

    public String getToken(String key) {
        return (String) redisTemplate.opsForHash().get(TOKEN_HASH_NAME, key);
    }

    public void deleteToken(String key) {
        redisTemplate.opsForHash().delete(TOKEN_HASH_NAME, key);
    }

    private String generateUuidKey() {
        return UUID.randomUUID().toString();
    }
}