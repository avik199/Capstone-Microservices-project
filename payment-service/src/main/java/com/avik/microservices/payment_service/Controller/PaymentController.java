package com.avik.microservices.payment_service.Controller;

import com.avik.microservices.payment_service.PaymentServiceApplication;
import com.avik.microservices.payment_service.Service.PaymentService;
import com.avik.microservices.payment_service.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment pay(@RequestParam UUID orderId,
                       @RequestParam String status) {
        log.info("Received payment request | OrderId: {} | Status: {}", orderId, status);
        return paymentService.processPayment(orderId, status);

    }
}
