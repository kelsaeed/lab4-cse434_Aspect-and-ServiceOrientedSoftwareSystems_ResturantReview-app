package com.example.lab4.aspects;

import com.example.lab4.annotations.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLockAspect {

    private final RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(redisLock)")
    public Object applyLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String key = redisLock.key();
        int timeout = redisLock.timeoutInSeconds();

        log.info("🔍 Trying to acquire lock with key: {}", key);

        // Try to acquire lock with expiration
        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, "LOCKED", timeout, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(acquired)) {
            log.info("✅ Lock acquired for key: {}", key);
            try {
                return joinPoint.proceed();
            } finally {
                redisTemplate.delete(key);
                log.info("🔓 Lock released for key: {}", key);
            }
        } else {
            log.warn("⛔ Lock not acquired for key: {}", key);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Another request is already in progress. Please try again later.");
        }
    }
}
