package com.nhnacademy.frontserver1.presentation.dto.response.address;

public record UserAddressResponse(
        Long addressId,
        String addressName,
        String addressDetail,
        boolean addressBased
) {}