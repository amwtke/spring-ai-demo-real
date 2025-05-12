package com.spring.ai.springaidemo.service;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MyDataService {
    private final RedissonClient redissonClient;

    public MyDataService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String getFromRedis(String key) {
        RBucket<String> value = redissonClient.getBucket(key);
        return value.get();
    }

    public void setKeyToRedis(String key, String value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public void setKeyToRedis(String key, String value, Duration duration) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value, duration);
    }
}

