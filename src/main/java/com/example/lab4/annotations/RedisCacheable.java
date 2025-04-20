package com.example.lab4.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheable {
    String key();
    int ttl() default 60;
}
