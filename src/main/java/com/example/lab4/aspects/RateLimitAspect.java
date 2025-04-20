package com.example.lab4.aspects;

import com.example.lab4.annotations.RateLimit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitAspect {

    private final RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(rateLimit)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = rateLimit.key();
        int limit = rateLimit.limit();
        int window = rateLimit.timeWindowInSeconds();

        // Get current count
        String value = redisTemplate.opsForValue().get(key);
        int currentCount = (value != null) ? Integer.parseInt(value) : 0;

        // Check if over the limit
        if (currentCount >= limit) {
            log.warn("⛔ Rate limit exceeded for key: {}", key);
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
        }

        // Increment count
        redisTemplate.opsForValue().increment(key);

        // Set TTL if key is new
        if (currentCount == 0) {
            redisTemplate.expire(key, Duration.ofSeconds(window));
        }

        log.info("✅ Rate limit OK for key: {}, count: {}/{}", key, currentCount + 1, limit);
        return joinPoint.proceed();
    }
}
