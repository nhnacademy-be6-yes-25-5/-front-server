package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
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
import org.springframework.http.ResponseEntity;
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


    @PostMapping("find/email")
    List<FindUserResponse> findByEmail(@RequestBody FindEmailRequest emailRequest, @RequestParam Pageable pageable);

    @GetMapping("/check-email")
    Boolean checkEmail(@RequestParam String email);

    @GetMapping("/coupons/state")
    Page<CouponBoxResponse> getStateCouponBox(@RequestParam String couponState, Pageable pageable);

    @PostMapping("/find/password")
    boolean findUserPasswordByEmailByName(@RequestBody FindPasswordRequest request);

//    @GetMapping
//    List<UserAddressResponse> findAllUserAddresses();

    @GetMapping("/user-addresses")
    Page<UserAddressResponse> findAllUserAddresses(Pageable pageble);


   // Page<UserAddressResponse> findAllUserAddresses(Long userId, Pageable pageable);



    //Operation(summary = "회원 주소 목록 조회", description = "회원의 모든 주소 목록을 조회합니다.")
    //    @GetMapping("/users/addressList")
    //    public ResponseEntity<Page<UserAddressResponse>> findAllUserAddresses(Pageable pageable,
    //                                                                          @CurrentUser JwtUserDetails jwtUserDetails) {
    //
    //        Long userId = jwtUserDetails.userId();
    //
    //        return ResponseEntity.ok(userAddressService.findAllAddresses(userId, pageable));
    //    }



}
