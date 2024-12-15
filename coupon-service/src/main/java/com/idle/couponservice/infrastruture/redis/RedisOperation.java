package com.idle.couponservice.infrastruture.redis;

import org.springframework.data.redis.core.RedisOperations;

@FunctionalInterface
public interface RedisOperation {
    void execute(RedisOperations<String, Object> operations, Object vo);
}