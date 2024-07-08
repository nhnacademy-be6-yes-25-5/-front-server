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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest createCartRequest) {
        return ResponseEntity.ok(cartService.createCart(createCartRequest));
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
