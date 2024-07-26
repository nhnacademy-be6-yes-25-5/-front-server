package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.UpdateCartBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.DeleteCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.UpdateCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;

public interface CartService {

    CreateCartResponse createCart(String cartId, CreateCartRequest createCartRequest);

    List<ReadCartBookResponse> getCarts(String cartId);

    UpdateCartBookResponse updateCart(String cartId, Long bookId, UpdateCartBookRequest request);

    DeleteCartBookResponse deleteCartBook(String cartId, Long bookId);

    List<ReadCartBookResponse> getCartsWithOrder(String cartId);
}
