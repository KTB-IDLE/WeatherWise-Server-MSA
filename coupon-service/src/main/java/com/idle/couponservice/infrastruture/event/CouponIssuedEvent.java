package com.idle.couponservice.infrastruture.event;


import com.idle.commonservice.event.Event;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CouponIssuedEvent extends Event {
    private Long userId;
    private Long couponId;

    public static CouponIssuedEvent create(Long userId , Long couponId) {
        return CouponIssuedEvent.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
    }
}
