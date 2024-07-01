package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UsersAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersAdaptor userAdaptor;

    @Override
    public UserResponse getUserById(Long id) {
        UserResponse user = userAdaptor.getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public Page<UserAddressResponse> getUserAddresses(Long userId, Pageable pageable) {
        UserResponse user = getUserById(userId);
        List<UserAddressResponse> addresses = user.addresses();

        if (addresses == null || addresses.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), addresses.size());
        List<UserAddressResponse> pagedAddresses = addresses.subList(start, end);

        return new PageImpl<>(pagedAddresses, pageable, addresses.size());
    }
}