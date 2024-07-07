package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdaptor userAdaptor;

    @Override
    public UserResponse signUp(CreateUserRequest userRequest) {

        return userAdaptor.signUp(userRequest);
    }

    @Override
    public UserResponse findByUser() {
        return userAdaptor.findByUserId();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest userRequest) {
        return userAdaptor.updateUser(userRequest);
    }

    @Override
    public void deleteUser(DeleteUserRequest userRequest) {
        userAdaptor.deleteUser(userRequest);
    }

//    @Override
//    public PointPolicyResponse getPointPolicies(PointPolicyRequest pointPolicyRequest) {
//        return null;
//    }

    @Override
    public UserGradeResponse getUserGrade() {
        return userAdaptor.getUserGrade();
    }

    @Override
    public Page<PointLogResponse> getPointLogs(Pageable pageable) {
        return userAdaptor.getUserPointLogs(pageable);
    }

    public ReadUserInfoResponse getUserPointsAndGrade() {
        return userAdaptor.getUserPointsAndGrade();
    }

    // fixme. 해당 기능 구현하지 않아 주석처리하였습니다.
    @Override
    public UsersResponse getUserById(Long id) {
//        UserResponse user = userAdaptor.getUserById(id);
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//        return user;

        return null;
    }

    @Override
    public Boolean isEmailDuplicate(String email) {
        return userAdaptor.checkEmail(email);
    }

    @Override
    public Page<UserAddressResponse> getUserAddresses(Long userId, Pageable pageable) {
        UsersResponse user = getUserById(userId);
        List<UserAddressResponse> addresses = user.addresses();

        if (addresses == null || addresses.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), addresses.size());
        List<UserAddressResponse> pagedAddresses = addresses.subList(start, end);

        return new PageImpl<>(pagedAddresses, pageable, addresses.size());
    }



    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    public List<FindUserResponse> findAllUserEmailByUserNameByUserPhone(String name, String phone, Pageable pageable) {
        try {
            FindEmailRequest request = FindEmailRequest.builder()
                    .name(name)
                    .phone(phone)
                    //.pageable(pageable)
                    .build();
            logger.info("프론트 Request: " + request.toString());
            return userAdaptor.findByEmail(request, pageable);

        } catch (Exception e) {

            logger.severe("프론트 Error in findAllUserEmailByUserNameByUserPhone: " + e.getMessage());
            e.printStackTrace();
            throw e;


        }

    }

    @Override
    public Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable) {
        return userAdaptor.getStateCouponBox(couponState, pageable);
    }
}