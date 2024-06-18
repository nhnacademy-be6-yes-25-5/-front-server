package com.nhnacademy.frontserver1.presentation.dto.response.order;

public record ReadShippingPolicyResponse(Long shippingPolicyId,
                                         Integer shippingPolicyFee,
                                         Integer shippingPolicyMinAmount) {

}
