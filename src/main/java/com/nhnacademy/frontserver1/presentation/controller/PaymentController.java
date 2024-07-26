package com.nhnacademy.frontserver1.presentation.controller;

import static com.nhnacademy.frontserver1.common.utils.SessionUtil.getIntegerListFromSession;
import static com.nhnacademy.frontserver1.common.utils.SessionUtil.getLongListFromSession;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.common.exception.ApplicationException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.SuccessPaymentResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody CreatePaymentRequest request, HttpSession session) {
        String orderId = (String) session.getAttribute("orderId");
        Integer totalAmount = (Integer) session.getAttribute("totalAmount");
        List<Long> bookIds = getLongListFromSession(session.getAttribute("bookIds"));
        List<Integer> quantities = getIntegerListFromSession(session.getAttribute("quantities"));

        if (!Objects.equals(request.orderId(), orderId) || !Objects.equals(request.amount(), totalAmount)) {
            log.error("결제 정보와 주문 정보가 일치하지 않습니다.");
            throw new ApplicationException(
                ErrorStatus.toErrorStatus("결제 정보가 주문 정보와 일치하지 않습니다.", 400, LocalDateTime.now()));
        }

        paymentService.createPayment(request, bookIds, quantities);

        session.removeAttribute("orderId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("bookIds");
        session.removeAttribute("quantities");

        return ResponseEntity.ok(SuccessPaymentResponse.from(orderId));
    }

    @PostMapping("/confirm/zero")
    public ResponseEntity<?> confirmZeroAmount(@RequestBody CreatePaymentRequest request, HttpSession session) {
        String orderId = (String) session.getAttribute("orderId");
        Integer totalAmount = (Integer) session.getAttribute("totalAmount");
        List<Long> bookIds = getLongListFromSession(session.getAttribute("bookIds"));
        List<Integer> quantities = getIntegerListFromSession(session.getAttribute("quantities"));

        if (!Objects.equals(request.orderId(), orderId) && !Objects.equals(request.amount(), totalAmount)) {
            log.error("결제 정보와 주문 정보가 일치하지 않습니다.");
            throw new ApplicationException(
                ErrorStatus.toErrorStatus("결제 정보가 주문 정보와 일치하지 않습니다.", 400, LocalDateTime.now()));
        }

        session.removeAttribute("orderId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("bookIds");
        session.removeAttribute("quantities");

        return ResponseEntity.ok(paymentService.createPaymentByZeroAmount(request, totalAmount, bookIds, quantities));
    }

    @GetMapping("/success")
    public String success(){
        return "order/success";
    }

    @GetMapping("/done/{orderId}")
    public String paymentDone(@PathVariable String orderId, Model model, @RequestParam Integer amount) {
        List<ReadPaymentOrderResponse> responses = paymentService.findAllOrderByOrderId(orderId);

        model.addAttribute("orders", responses);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "order/done";
    }

    @GetMapping("/fail")
    public String fail(){
        return "order/fail";
    }
}
