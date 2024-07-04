package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadOrderNoneMemberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.UpdateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMaximumDiscountCouponResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMyOrderHistoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDeliveryInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDetailResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPurePriceResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.UpdateOrderResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    CreateOrderResponse createPreOrder(CreateOrderRequest request);

    ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable, Integer totalAmount);

    ReadShippingPolicyResponse findFreePolicy();

    List<ReadTakeoutResponse> findAllTakeout();

    List<ReadCartBookResponse> getOrderBook(Long bookId, Integer quantity);

    ReadOrderStatusResponse getOrderStatusByOrderId(String orderId);

    Page<ReadOrderUserAddressResponse> getUserAddresses(Pageable pageable);

    ReadOrderUserInfoResponse getUserInfo();

    ReadMaximumDiscountCouponResponse getMaxDiscountCoupon(Integer totalAmount);

    Page<ReadMyOrderHistoryResponse> getMyOrders(Pageable pageable);

    ReadPurePriceResponse getPurePrice();

    UpdateOrderResponse updateOrderByOrderId(String orderId, UpdateOrderRequest request);

    ReadOrderDeliveryInfoResponse getMyOrderDelivery(String orderId);

    ReadOrderDetailResponse getMyOrderByOrderId(String orderId);

    ReadOrderDetailResponse findOrderNoneMemberByOrderIdAndEmail(ReadOrderNoneMemberRequest request);
}
