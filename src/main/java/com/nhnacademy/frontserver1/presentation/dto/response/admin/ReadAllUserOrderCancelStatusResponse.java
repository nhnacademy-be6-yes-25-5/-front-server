package com.nhnacademy.frontserver1.presentation.dto.response.admin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReadAllUserOrderCancelStatusResponse(String orderId,
                                                   Long userId,
                                                   List<String> bookNames,
                                                   List<Long> bookIds,
                                                   LocalDate canceledAt,
                                                   BigDecimal amount) {

}
