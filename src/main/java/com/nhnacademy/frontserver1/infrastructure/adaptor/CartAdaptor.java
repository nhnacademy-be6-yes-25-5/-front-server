package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadCartBookResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cartAdaptor", url = "${eureka.gateway}/users")
public interface CartAdaptor {

    @GetMapping("/cart-books")
    List<ReadCartBookResponse> getCartBooks();
}
