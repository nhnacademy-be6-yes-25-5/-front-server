package com.nhnacademy.frontserver1.presentation.dto.response.address;

public record UsersAddressResponse(
        Long addressId,
        String addressName,
        String addressDetail,
        boolean addressBased
) {}