package com.example.lab4.services;

import com.example.lab4.annotations.RateLimit;
import com.example.lab4.annotations.RedisCacheable;
import com.example.lab4.annotations.RedisLock;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @RateLimit(key = "user:rate", limit = 3, timeWindowInSeconds = 60)
    @RedisLock(key = "user:lock", timeoutInSeconds = 10)
    @RedisCacheable(key = "user:data", ttl = 30)
    public String getUserData() {
        try {
            Thread.sleep(10000); // simulate DB delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "User Data from DB at: " + System.currentTimeMillis();
    }
}
