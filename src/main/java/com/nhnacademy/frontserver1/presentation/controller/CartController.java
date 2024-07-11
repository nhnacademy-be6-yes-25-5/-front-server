package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CartService;
import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.UpdateCartBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.DeleteCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.UpdateCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nhnacademy.frontserver1.common.context.TokenContext;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest createCartRequest) {
        CreateCartResponse response = cartService.createCart(createCartRequest);

        // 쿠키 생성
        HttpCookie accessTokenCookie = ResponseCookie.from("AccessToken", TokenContext.getAccessToken())
                .httpOnly(true)
                .secure(true) // HTTPS를 사용하는 경우에만 true로 설정
                .path("/")
                .maxAge(Duration.ofHours(1)) // 예: 1시간 유효
                .sameSite("Strict") // 또는 "Lax"
                .build();

        HttpCookie refreshTokenCookie = ResponseCookie.from("RefreshToken", TokenContext.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7)) // 예: 7일 유효
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @GetMapping
    public String getCart(Model model, Pageable pageable) {
        List<ReadCartBookResponse> response = cartService.getCarts();

        BigDecimal totalPrice = response.stream()
            .map(cart -> cart.bookPrice().multiply(BigDecimal.valueOf(cart.cartBookQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReadShippingPolicyResponse shippingPolicy = orderService.findAllOrderPolicy(pageable, totalPrice.intValue());
        BigDecimal shippingFee = BigDecimal.valueOf(shippingPolicy.shippingPolicyFee());
        BigDecimal finalTotalPrice = totalPrice.add(shippingFee);

        model.addAttribute("totalPrice", totalPrice.intValue());
        model.addAttribute("carts", response);
        model.addAttribute("finalTotalPrice", finalTotalPrice.intValue());
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("shippingPolicy", shippingPolicy);

        return "cart/cart";
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<UpdateCartBookResponse> updateCart(@PathVariable Long bookId,
        @RequestBody UpdateCartBookRequest request) {
        return ResponseEntity.ok(cartService.updateCart(bookId, request));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<DeleteCartBookResponse> deleteCartBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.deleteCartBook(bookId));
    }

}
