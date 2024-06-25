package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.util.List;
import lombok.Builder;

@Builder
public record ReadOrderUserInfoResponse(Long userId,
                                        Integer points,
                                        List<String> addressRaw,
                                        List<String> addressDetail,
                                        List<String> addressName,
                                        List<String> zipCode,
                                        List<Boolean> addressBased) {

    public static ReadOrderUserInfoResponse fromTest() {
        return ReadOrderUserInfoResponse.builder()
            .userId(8L)
            .points(10000)
            .addressRaw(List.of("여기는", "호오오"))
            .addressDetail(List.of("조선대", "그런데"))
            .addressName(List.of("학교", "우리집"))
            .zipCode(List.of("12345", "56789"))
            .addressBased(List.of(true, false))
            .build();
    }
}
