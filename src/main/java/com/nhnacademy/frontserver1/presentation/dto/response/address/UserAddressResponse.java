package com.nhnacademy.frontserver1.presentation.dto.response.address;

import lombok.Builder;


@Builder
public record UserAddressResponse(
        Long userAddressId,
        Long addressId,
        String addressZip,
        String addressRaw,
        String addressName,
        String addressDetail,
        boolean addressBased,
        Long userId
) {
}

