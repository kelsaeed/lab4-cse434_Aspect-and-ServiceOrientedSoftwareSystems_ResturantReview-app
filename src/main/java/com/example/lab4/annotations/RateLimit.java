package com.example.lab4.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key();
    int limit();
    int timeWindowInSeconds();
}
