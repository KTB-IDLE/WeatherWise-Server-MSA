package com.idle.couponservice.application;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockFacade {

    private final CouponService couponService;

    @Retryable(
            value = { OptimisticLockingFailureException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 100, multiplier = 2)
    )
    public void receiveCoupon(Long userId, Long couponId){
        couponService.receiveCoupon(userId,couponId);
    }
}