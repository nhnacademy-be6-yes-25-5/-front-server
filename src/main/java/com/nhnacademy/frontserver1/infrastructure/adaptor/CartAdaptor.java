package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.UpdateCartBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.DeleteCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.UpdateCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cartAdaptor", url = "${eureka.gateway}/users")
public interface CartAdaptor {

    @GetMapping("/cart-books/{cartId}")
    List<ReadCartBookResponse> getCartBooks(@PathVariable String cartId);

    @PostMapping("/cart-books/{cartId}")
    ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest createCartRequest,
        @PathVariable String cartId);

    @PutMapping("/cart-books/{cartId}/books/{bookId}")
    UpdateCartBookResponse updateCartBook(@PathVariable String cartId, @PathVariable Long bookId,
        @RequestBody UpdateCartBookRequest request);

    @DeleteMapping("/cart-books/{cartId}/books/{bookId}")
    DeleteCartBookResponse deleteCartBookByBookId(@PathVariable String cartId, @PathVariable Long bookId);
}
