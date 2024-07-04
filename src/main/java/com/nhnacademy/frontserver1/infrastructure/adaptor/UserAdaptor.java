package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPurePriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "userAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface UserAdaptor {

    @PostMapping("/sign-up")
    UserResponse signUp(@RequestBody CreateUserRequest userRequest);

    @GetMapping("/info")
    UserResponse findByUserId();

    @PutMapping("/info")
    UpdateUserResponse updateUser(@RequestBody UpdateUserRequest userRequest);

    @DeleteMapping("/delete")
    void deleteUser(@RequestBody DeleteUserRequest userRequest);

    @GetMapping("/orders/info")
    ReadOrderUserInfoResponse getUserInfo();

    @PostMapping("/coupons/claim")
    void claimCoupon(@RequestParam Long couponId);

    @GetMapping("/pure-price")
    ReadPurePriceResponse getPurePrice();

    @GetMapping("/grade")
    ReadUserInfoResponse getUserPointsAndGrade();

    @GetMapping("/grades")
    UserGradeResponse getUserGrade();

    @GetMapping("/point-logs")
    Page<PointLogResponse> getUserPointLogs(Pageable pageable);

    @GetMapping("/check-email")
    Boolean checkEmail(@RequestParam String email);

    @GetMapping("/coupons/state")
    Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable);

//    @GetMapping("/coupons/active")
//    Page<CouponBoxResponse> getActiveCouponBox(Pageable pageable);
//
//    @GetMapping("/coupons/used")
//    Page<CouponBoxResponse> getUsedCouponBox(Pageable pageable);
//
//    @GetMapping("/coupons/expired")
//    Page<CouponBoxResponse> getExpiredCouponBox(Pageable pageable);
}
