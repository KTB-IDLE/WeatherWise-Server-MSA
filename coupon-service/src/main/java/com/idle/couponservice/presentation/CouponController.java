package com.idle.couponservice.presentation;

import com.idle.commonservice.annotation.UserId;
import com.idle.commonservice.base.BaseResponse;
import com.idle.couponservice.application.CouponIssuedService;
import com.idle.couponservice.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;
    private final CouponIssuedService couponIssuedService;

    /**
     * 내가 보유하고 있는 CouponList 확인하기
     */
    @GetMapping
    public void getCouponList(@UserId Long userId) {
        couponService.getCouponList(userId);
    }

    /**
     * Coupon 발급 받기
     * V0 : Coupon 에서 모두 처리
     * V1 : Aggregator 를 사용하여 처리
     */
    @PostMapping("/{coupon-id}")
    public void receiveCoupon(@UserId Long userId , @PathVariable("coupon-id") Long couponId) {
        // couponService.receiveCoupon(userId , couponId);
        boolean isSuccess = couponIssuedService.allocateStock(couponId, userId);
        if (isSuccess) log.info("지급 완료");
        else log.info("지급 실패");
    }
}
