package com.idle.couponservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Slf4j
public class Coupon extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Embedded
    private CouponDateInfo couponDateInfo;

    @Embedded
    private CouponDiscountInfo couponDiscountInfo;

    @Column(name = "coupon_name")
    private String name;

    @Column(name = "quantity")
    // @Version
    private int quantity;

    public boolean checkQuantity() {
        log.info("currentQuantity = {} " , this.quantity);
        return this.quantity--  > 0;
    }
}
