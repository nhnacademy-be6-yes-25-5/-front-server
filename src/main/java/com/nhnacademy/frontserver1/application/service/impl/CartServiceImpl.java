package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CartService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CartAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.UpdateCartBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.DeleteCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.UpdateCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartAdaptor cartAdaptor;

    @Override
    public CreateCartResponse createCart(String cartId, CreateCartRequest createCartRequest) {
        return cartAdaptor.createCart(createCartRequest, cartId).getBody();
    }

    @Override
    public List<ReadCartBookResponse> getCarts(String cartId) {
        return cartAdaptor.getCartBooks(cartId);
    }

    @Override
    public UpdateCartBookResponse updateCart(String cartId, Long bookId, UpdateCartBookRequest request) {
        return cartAdaptor.updateCartBook(cartId, bookId, request);
    }

    @Override
    public DeleteCartBookResponse deleteCartBook(String cartId, Long bookId) {
        return cartAdaptor.deleteCartBookByBookId(cartId, bookId);
    }
}
