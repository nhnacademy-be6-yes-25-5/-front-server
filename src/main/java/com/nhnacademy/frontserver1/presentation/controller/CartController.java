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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest createCartRequest,
        HttpServletRequest request, HttpServletResponse response) {
        String cartId = generateCartIdWhenCartCookieNotFound(request);
        CreateCartResponse cartResponse = cartService.createCart(cartId, createCartRequest);

        addCartCookieWhenCartCookieNotFound(request, response, cartId);

        return ResponseEntity.ok(cartResponse);
    }

    private String generateCartIdWhenCartCookieNotFound(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CART-ID")) {
                    return cookie.getValue();
                }
            }
        }

        return UUID.randomUUID().toString();
    }

    private void addCartCookieWhenCartCookieNotFound(HttpServletRequest request, HttpServletResponse response, String cartId) {
        boolean cartIdCookieFound = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("CART-ID".equals(cookie.getName())) {
                    cartIdCookieFound = true;
                    break;
                }
            }
        }

        if (cookies == null || !cartIdCookieFound) {
            Cookie cartCookie = new Cookie("CART-ID", cartId);
            cartCookie.setHttpOnly(true);
            cartCookie.setPath("/");
            cartCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cartCookie);
        }
    }

    @GetMapping
    public String getCart(Model model, Pageable pageable, HttpServletRequest request) {
        String cartId = getCartIdFromCookie(request);
        List<ReadCartBookResponse> response = cartService.getCarts(cartId);

        BigDecimal totalPrice = response.stream()
            .map(cart -> cart.bookPrice().multiply(BigDecimal.valueOf(cart.cartBookQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReadShippingPolicyResponse shippingPolicy = orderService.findAllOrderPolicy(pageable, totalPrice.intValue());
        BigDecimal shippingFee = BigDecimal.valueOf(shippingPolicy.shippingPolicyFee());
        BigDecimal finalTotalPrice = totalPrice.add(shippingFee);

        model.addAttribute("cartId", cartId);
        model.addAttribute("totalPrice", totalPrice.intValue());
        model.addAttribute("carts", response);
        model.addAttribute("finalTotalPrice", finalTotalPrice.intValue());
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("shippingPolicy", shippingPolicy);

        return "cart/cart";
    }

    private String getCartIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("CART-ID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    @PutMapping("/{cartId}/books/{bookId}")
    public ResponseEntity<UpdateCartBookResponse> updateCart(@PathVariable Long bookId,
        @PathVariable String cartId,
        @RequestBody UpdateCartBookRequest request) {
        return ResponseEntity.ok(cartService.updateCart(cartId, bookId, request));
    }

    @DeleteMapping("/{cartId}/books/{bookId}")
    public ResponseEntity<DeleteCartBookResponse> deleteCartBook(@PathVariable Long bookId,
        @PathVariable String cartId) {
        return ResponseEntity.ok(cartService.deleteCartBook(cartId, bookId));
    }

}
