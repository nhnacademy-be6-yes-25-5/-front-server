package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CartService;
import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.domain.TakeoutType;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadOrderNoneMemberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.UpdateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.ReadAvailableUserCouponResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDetailResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.UpdateOrderResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private static final String MEMBER = "MEMBER";

    @GetMapping("/checkout")
    public String findAllCheckout(Model model, Pageable pageable, HttpServletRequest request) {
        String cartId = getCartIdFromCookie(request);
        String orderId = UUID.randomUUID().toString();
        List<ReadCartBookResponse> cartBookResponses = cartService.getCartsWithOrder(cartId);
        ReadOrderUserInfoResponse orderUserInfoResponse = orderService.getUserInfo(request);
        Integer totalAmount = getTotalAmount(cartBookResponses);

        populateUserInfo(model, orderUserInfoResponse, totalAmount, orderId, cartId);
        populateOrderDetails(model, pageable, totalAmount, cartBookResponses);

        return "order/checkout";
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

    private void populateUserInfo(Model model, ReadOrderUserInfoResponse orderUserInfoResponse,
        Integer totalAmount, String orderId, String cartId) {
        model.addAttribute("userInfo", orderUserInfoResponse);
        model.addAttribute("orderId", orderId);
        model.addAttribute("cartId", cartId);

        if (MEMBER.equals(orderUserInfoResponse.role())) {
            model.addAttribute("orderUserName", orderUserInfoResponse.name());
            model.addAttribute("orderUserEmail", orderUserInfoResponse.email());
            model.addAttribute("orderUserPhoneNumber", orderUserInfoResponse.phoneNumber());
            model.addAttribute("points", orderUserInfoResponse.points());

            // fixme. 추후 수정할 예정입니다.
//            model.addAttribute("maxDiscountCoupon", orderService.getMaxDiscountCoupon(totalAmount));
        } else {
            model.addAttribute("orderUserName", "");
            model.addAttribute("orderUserEmail", "");
            model.addAttribute("orderUserPhoneNumber", "");
            model.addAttribute("points", 0);
            model.addAttribute("maxDiscountCoupon", null);
        }
    }

    private void populateOrderDetails(Model model, Pageable pageable, Integer totalAmount,
        List<ReadCartBookResponse> cartBookResponses) {
        ReadShippingPolicyResponse shippingPolicy = orderService.findAllOrderPolicy(pageable,
            totalAmount);
        ReadShippingPolicyResponse freeShippingPolicy = orderService.findFreePolicy();
        Integer freeShippingAmount = freeShippingPolicy.shippingPolicyMinAmount() - totalAmount;
        List<ReadTakeoutResponse> takeoutResponses = orderService.findAllTakeout();

        boolean hasUnPackableBook = cartBookResponses.stream()
            .anyMatch(cartBook -> !cartBook.bookIsPackable());

        List<ReadTakeoutResponse> availableTakeouts = hasUnPackableBook
            ? List.of(new ReadTakeoutResponse(TakeoutType.NONE, 0, "포장 불가"))
            : takeoutResponses;

        model.addAttribute("shippingPolicy", shippingPolicy);
        model.addAttribute("freeShippingPolicy", freeShippingPolicy);
        model.addAttribute("freeShippingAmount", freeShippingAmount);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("cartBooks", cartBookResponses);
        model.addAttribute("takeouts", availableTakeouts);
    }

    private Integer getTotalAmount(List<ReadCartBookResponse> cartBookResponses) {
        BigDecimal totalAmount = cartBookResponses.stream()
            .map(cartBookResponse -> cartBookResponse.bookPrice()
                .multiply(BigDecimal.valueOf(cartBookResponse.cartBookQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAmount.intValue();
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest request,
        HttpSession session) {
        log.info("주문 요청이 들어왔습니다. {}", request.toString());
        CreateOrderResponse response = orderService.createPreOrder(request);

        session.setAttribute("orderId", response.orderId());
        session.setAttribute("totalAmount", response.totalAmount());
        session.setAttribute("bookIds", response.bookIds());
        session.setAttribute("quantities", response.quantities());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fail")
    public String getErrorPage() {
        return "error/order-fail";
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<ReadOrderStatusResponse> getOrderStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderStatusByOrderId(orderId));
    }

    @GetMapping("/address")
    public String getUserAddress(Pageable pageable, Model model) {
        Page<ReadOrderUserAddressResponse> responses = orderService.getUserAddresses(pageable);
        model.addAttribute("addresses", responses);

        return "order/address-popup";
    }

    @GetMapping("/coupons")
    public String getCoupons(@RequestParam("bookId") Long bookId, @RequestParam("categoryIds") List<Long> categoryIds, Model model) {
        List<ReadAvailableUserCouponResponse> coupons = orderService.getUserCoupons(bookId, categoryIds);
        model.addAttribute("coupons", coupons);
        return "coupon/popup";
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<UpdateOrderResponse> updateOrder(@PathVariable String orderId,
        @RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrderByOrderId(orderId, request));
    }

    @GetMapping("/find")
    public String getOrderFindPage() {
        return "order/orders-find";
    }

    @GetMapping("/none")
    public String getOrderNoneMember(@ModelAttribute ReadOrderNoneMemberRequest request,
        Model model) {
        ReadOrderDetailResponse response = orderService.findOrderNoneMemberByOrderIdAndEmail(
            request);
        model.addAttribute("order", response);
        model.addAttribute("noneMember", "noneMember");

        return "mypage/mypage-orders-detail";
    }
}
