package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cartAdaptor", url = "${eureka.gateway}/users")
public interface CartAdaptor {

    @GetMapping("/cart-books")
    List<ReadCartBookResponse> getCartBooks();

    @PostMapping("/cart-books")
    CreateCartResponse createCart(CreateCartRequest createCartRequest);
}
