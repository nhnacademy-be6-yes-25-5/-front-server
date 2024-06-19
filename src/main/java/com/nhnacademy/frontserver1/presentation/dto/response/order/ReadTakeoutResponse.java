package com.nhnacademy.frontserver1.presentation.dto.response.order;

import com.nhnacademy.frontserver1.domain.TakeoutType;
import lombok.Builder;

@Builder
public record ReadTakeoutResponse(TakeoutType takeoutType, Integer takeoutPrice, String takeoutDescription) {

}
