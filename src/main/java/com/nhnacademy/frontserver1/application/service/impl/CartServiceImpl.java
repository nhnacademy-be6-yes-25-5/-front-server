package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CartService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CartAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.cart.CreateCartRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.cart.CreateCartResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartAdaptor cartAdaptor;

    @Override
    public CreateCartResponse createCart(CreateCartRequest createCartRequest) {
        return cartAdaptor.createCart(createCartRequest).getBody();
    }

    @Override
    public List<ReadCartBookResponse> getCarts() {
        return cartAdaptor.getCartBooks();
    }
}
