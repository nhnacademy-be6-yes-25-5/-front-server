package com.nhnacademy.frontserver1.presentation.dto.response.order;

public record ReadOrderUserAddressResponse(Long userAddressId,
                                           String addressRaw,
                                           String addressDetail,
                                           String addressName,
                                           String zipCode,
                                           String reference,
                                           Boolean addressBased) {

}
