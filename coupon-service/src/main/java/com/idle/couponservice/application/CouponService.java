package com.idle.couponservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponRepository;
import com.idle.couponservice.domain.user.User;
import com.idle.couponservice.infrastruture.CreatedMissionJpaRepository;
import com.idle.couponservice.infrastruture.UserJpaRepository;

import com.idle.couponservice.infrastruture.event.CouponIssuedEvent;
import com.idle.couponservice.infrastruture.stream.out.CouponIssuedEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserJpaRepository userRepository;
    private final CreatedMissionJpaRepository createdMissionRepository;
    private final CouponIssuedEventPublisher couponIssuedEventPublisher;


    public void getCouponList(Long userId) {

    }
    
    @Transactional
    public void receiveCoupon(Long userId, Long couponId) {
        boolean hasCoupon = userRepository.hasCoupon(userId, couponId);

        if (hasCoupon) {
            throw new BaseException(ErrorCode.ALREADY_ISSUED_COUPON);
        }

        boolean hasCompletedMissionToday = createdMissionRepository
                .hasCompletedMissionToday(userId, LocalDateTime.now());

        if (!hasCompletedMissionToday) {
            throw new BaseException(ErrorCode.NOT_COMPLETED_ANY_MISSION);
        }

        Coupon coupon = couponRepository.findById(couponId);
        boolean couponQuantity = coupon.checkQuantity();
        if (!couponQuantity) {
            throw new BaseException(ErrorCode.EXCEEDED_QUANTITY);
        }

        // 쿠폰 발행
        couponIssuedEventPublisher.publishCouponIssuedEvent(CouponIssuedEvent.create(userId , couponId));
    }
}
