package com.idle.couponservice.infrastruture.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisTransaction {

    public Object execute(
            RedisOperations<String, Object> redisTemplate, RedisOperation operation, Object vo) {

        return redisTemplate.execute(
                new SessionCallback<Object>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public Object execute(RedisOperations operations) {
                        // 트랜잭션 시작
                        operations.multi();

                        // 커맨드 실행
                        operation.execute(operations, vo);

                        // 트랜잭션 실행
                        return operations.exec();
                    }
                }
        );
    }
}
