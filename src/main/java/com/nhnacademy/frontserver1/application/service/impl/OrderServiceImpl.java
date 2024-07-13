package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AddressAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.OrderAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.OrderBookAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PolicyAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserCouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
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
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadUserCouponResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.UpdateOrderResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderAdaptor orderAdaptor;
    private final PolicyAdaptor policyAdaptor;
    private final AddressAdaptor addressAdaptor;
    private final UserAdaptor userAdaptor;
    private final UserCouponAdaptor userCouponAdaptor;
    private final OrderBookAdaptor orderBookAdaptor;

    @Override
    public CreateOrderResponse createPreOrder(CreateOrderRequest request) {
        return orderAdaptor.createPreOrder(request);
    }

    @Override
    public ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable,
        Integer totalAmount) {
        Page<ReadShippingPolicyResponse> policyResponses = policyAdaptor.findAllDeliveryPolicy(pageable);

        return policyResponses.getContent().stream()
            .filter(policy -> totalAmount >= policy.shippingPolicyMinAmount())
            .min(Comparator.comparingInt(ReadShippingPolicyResponse::shippingPolicyFee))
            .orElse(null);
    }

    @Override
    public ReadShippingPolicyResponse findFreePolicy() {
        return policyAdaptor.findFreePolicy();
    }

    @Override
    public List<ReadTakeoutResponse> findAllTakeout() {
        return policyAdaptor.findAllTakeoutPolicy();
    }

    @Override
    public ReadOrderStatusResponse getOrderStatusByOrderId(String orderId) {
        return orderAdaptor.findOrderStatusByOrderId(orderId);
    }

    @Override
    public Page<ReadOrderUserAddressResponse> getUserAddresses(Pageable pageable) {
        return addressAdaptor.getUserAddresses(pageable);
    }

    @Override
    public ReadOrderUserInfoResponse getUserInfo(HttpServletRequest request) {
        if (hasTokenCookie(request)) {
            return userAdaptor.getUserInfo();
        }

        return ReadOrderUserInfoResponse.fromNoneMember();
    }

    private boolean hasTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AccessToken") || cookie.getName()
                    .equals("RefreshToken")) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ReadMaximumDiscountCouponResponse getMaxDiscountCoupon(Integer totalAmount) {
        return userCouponAdaptor.getMaxDiscountCouponByTotalAmount(totalAmount);
    }

    @Override
    public Page<ReadMyOrderHistoryResponse> getMyOrders(Pageable pageable) {
        return orderAdaptor.getMyOrders(pageable);
    }

    @Override
    public ReadPurePriceResponse getPurePrice() {
        return userAdaptor.getPurePrice();
    }

    @Override
    public UpdateOrderResponse updateOrderByOrderId(String orderId, UpdateOrderRequest request) {
        return orderAdaptor.updateOrderByOrderId(orderId, request);
    }

    @Override
    public ReadOrderDeliveryInfoResponse getMyOrderDelivery(String orderId) {
        return orderAdaptor.getMyOrderDeliveryByOrderId(orderId);
    }

    @Override
    public ReadOrderDetailResponse getMyOrderByOrderId(String orderId) {
        return orderAdaptor.getMyOrderByOrderId(orderId);
    }

    @Override
    public ReadOrderDetailResponse findOrderNoneMemberByOrderIdAndEmail(ReadOrderNoneMemberRequest request) {
        return orderAdaptor.findOrderNoneMemberByOrderIdAndEmail(request.orderId(), request.email());
    }

    @Override
    public List<ReadUserCouponResponse> getUserCoupons() {
        return userCouponAdaptor.getAllUserCoupons();
    }
}
