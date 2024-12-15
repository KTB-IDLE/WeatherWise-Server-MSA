package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u JOIN u.coupons c " +
            "WHERE u.id = :userId AND c.couponId.couponId = :couponId")
    boolean hasCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId);

}
