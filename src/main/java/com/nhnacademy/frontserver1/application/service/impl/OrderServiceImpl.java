package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AddressAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.OrderAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PolicyAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserCouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadOrderNoneMemberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.UpdateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.ReadAvailableUserCouponResponse;
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

    @Override
    public CreateOrderResponse createPreOrder(CreateOrderRequest request) {
        return orderAdaptor.createPreOrder(request).getBody();
    }

    @Override
    public ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable,
        Integer totalAmount) {
        Page<ReadShippingPolicyResponse> policyResponses = policyAdaptor.findAllDeliveryPolicy(
            pageable).getBody();

        return policyResponses.getContent().stream()
            .filter(policy -> totalAmount >= policy.shippingPolicyMinAmount())
            .min(Comparator.comparingInt(ReadShippingPolicyResponse::shippingPolicyFee))
            .orElse(null);
    }

    @Override
    public ReadShippingPolicyResponse findFreePolicy() {
        return policyAdaptor.findFreePolicy().getBody();
    }

    @Override
    public List<ReadTakeoutResponse> findAllTakeout() {
        return policyAdaptor.findAllTakeoutPolicy().getBody();
    }

    @Override
    public ReadOrderStatusResponse getOrderStatusByOrderId(String orderId) {
        return orderAdaptor.findOrderStatusByOrderId(orderId).getBody();
    }

    @Override
    public Page<ReadOrderUserAddressResponse> getUserAddresses(Pageable pageable) {
        return addressAdaptor.getUserAddresses(pageable);
    }

    @Override
    public ReadOrderUserInfoResponse getUserInfo(HttpServletRequest request) {
        if (hasTokenCookie(request)) {
            return userAdaptor.getUserInfo().getBody();
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
        return orderAdaptor.getMyOrders(pageable).getBody();
    }

    @Override
    public ReadPurePriceResponse getPurePrice() {
        return userAdaptor.getPurePrice().getBody();
    }

    @Override
    public UpdateOrderResponse updateOrderByOrderId(String orderId, UpdateOrderRequest request) {
        return orderAdaptor.updateOrderByOrderId(orderId, request).getBody();
    }

    @Override
    public ReadOrderDeliveryInfoResponse getMyOrderDelivery(String orderId) {
        return orderAdaptor.getMyOrderDeliveryByOrderId(orderId).getBody();
    }

    @Override
    public ReadOrderDetailResponse getMyOrderByOrderId(String orderId) {
        return orderAdaptor.getMyOrderByOrderId(orderId).getBody();
    }

    @Override
    public ReadOrderDetailResponse findOrderNoneMemberByOrderIdAndEmail(
        ReadOrderNoneMemberRequest request) {
        return orderAdaptor.findOrderNoneMemberByOrderIdAndEmail(request.orderId(), request.email())
            .getBody();
    }

    @Override
    public List<ReadAvailableUserCouponResponse> getUserCoupons(Long bookId,
        List<Long> categoryIds) {
        List<ReadUserCouponResponse> userCoupons = userCouponAdaptor.getAllUserCoupons().getBody();

        assert userCoupons != null;
        return userCoupons.stream()
            .map(coupon -> {
                boolean isAvailable = coupon.applyCouponToAllBooks();

                if (!isAvailable) {
                    isAvailable = (coupon.bookId() != null && coupon.bookId().equals(bookId)) ||
                            (coupon.categoryIds() != null && coupon.categoryIds().stream()
                                .anyMatch(categoryIds::contains));
                }

                return ReadAvailableUserCouponResponse.of(coupon, isAvailable);
            }).toList();
    }
}
