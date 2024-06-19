package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponResponseDTO {
    private Long couponId;
    private String couponName;
    private String couponCode;
    private Date couponExpiredAt;
    private Date couponCreatedAt;
    private Long couponPolicyId;

    public CouponResponseDTO(Long couponId, String couponName, String couponCode, Date couponExpiredAt, Date couponCreatedAt, Long couponPolicyId) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.couponExpiredAt = couponExpiredAt;
        this.couponCreatedAt = couponCreatedAt;
        this.couponPolicyId = couponPolicyId;
    }

}
