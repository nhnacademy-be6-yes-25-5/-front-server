package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

@Builder
public record UpdateUserAddressResponse(String addressZip, String addressRaw, String addressName, String addressDetail, boolean addressBased) {
}
