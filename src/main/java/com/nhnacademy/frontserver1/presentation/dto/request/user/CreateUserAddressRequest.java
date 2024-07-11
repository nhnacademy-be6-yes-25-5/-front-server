package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

@Builder
public record CreateUserAddressRequest(String addressZip, String addressRaw, String addressName, String addressDetail, boolean addressBased) {
}
