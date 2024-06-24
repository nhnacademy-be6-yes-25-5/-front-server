package com.nhnacademy.frontserver1.presentation.controller;

import static com.nhnacademy.frontserver1.common.utils.SessionUtil.getIntegerListFromSession;
import static com.nhnacademy.frontserver1.common.utils.SessionUtil.getLongListFromSession;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public String confirm(@RequestBody CreatePaymentRequest request, HttpSession session) {
        String orderId = (String) session.getAttribute("orderId");
        Integer totalAmount = (Integer) session.getAttribute("totalAmount");
        List<Long> bookIds = getLongListFromSession(session.getAttribute("bookIds"));
        List<Integer> quantities = getIntegerListFromSession(session.getAttribute("quantities"));

        if (!Objects.equals(request.orderId(), orderId)
            || !Objects.equals(request.amount(), totalAmount)) {
            return "redirect:/payments/fail";
        }

        if (paymentService.createPayment(request, bookIds, quantities).status() != 200) {
            return "redirect:/payments/fail";
        }

        session.removeAttribute("orderId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("bookIds");
        session.removeAttribute("quantities");

        return "order/success";
    }

    @GetMapping("/success")
    public String success(){
        return "order/success";
    }

    @GetMapping("/fail")
    public String fail(){
        return "order/fail";
    }
}
