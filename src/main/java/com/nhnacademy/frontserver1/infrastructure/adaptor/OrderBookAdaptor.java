package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderBookInfoResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderBookAdaptor", url = "${eureka.gateway}/books", configuration = FeignClientConfig.class)
public interface OrderBookAdaptor {

    @GetMapping("/orders")
    ResponseEntity<List<ReadOrderBookInfoResponse>> getBooksByBookIdList(@RequestParam List<Long> bookIdList);
}
