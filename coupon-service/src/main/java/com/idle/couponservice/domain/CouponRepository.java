package com.idle.couponservice.domain;

import jakarta.persistence.LockModeType;

import java.util.List;

public interface CouponRepository {
    Coupon findById(Long couponId);
    Coupon findByIdWithLock(Long couponId);
}
