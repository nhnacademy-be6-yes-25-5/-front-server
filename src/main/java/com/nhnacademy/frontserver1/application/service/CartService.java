package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.UpdateCartBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.DeleteCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.UpdateCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;

public interface CartService {

    CreateCartResponse createCart(CreateCartRequest createCartRequest);

    List<ReadCartBookResponse> getCarts();

    UpdateCartBookResponse updateCart(Long bookId, UpdateCartBookRequest request);

    DeleteCartBookResponse deleteCartBook(Long bookId);
}
