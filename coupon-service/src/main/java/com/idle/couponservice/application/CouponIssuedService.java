package com.idle.couponservice.application;

import com.idle.couponservice.infrastruture.redis.RedisTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponIssuedService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTransaction redisTransaction;

    private static final String STOCK_KEY = "stock:";
    private static final String USER_SET_KEY = "users:";
    private static final String CREATED_MISSION_COMPLETED_KEY = "createdMission:completed:";

    public boolean allocateStock(Long couponId, Long userId) {
        String stockKey = STOCK_KEY + couponId;
        String userSetKey = USER_SET_KEY + userId;

        Object result = redisTransaction.execute(redisTemplate, (operations, vo) -> {
            // [1] 미션 인증 여부 확인
            Boolean isMissionCompleted = operations.opsForSet().isMember(CREATED_MISSION_COMPLETED_KEY, userId);
            if (Boolean.FALSE.equals(isMissionCompleted)) {
                throw new IllegalArgumentException("User has not completed the mission");
            }

            // [2] 사용자가 이미 지급받았는지 체크
            Boolean isUserExists = operations.opsForSet().isMember(userSetKey, userId);
            if (Boolean.TRUE.equals(isUserExists)) {
                throw new IllegalArgumentException("User has already received the stock");
            }

            // [3] 재고 감소
            Long stockCount = operations.opsForValue().decrement(stockKey);
            if (stockCount == null || stockCount < 0) {
                throw new IllegalStateException("Stock is not available");
            }

            // [4] 사용자 기록 추가
            operations.opsForSet().add(userSetKey, userId);
        }, null);

        return result != null;
    }
}
