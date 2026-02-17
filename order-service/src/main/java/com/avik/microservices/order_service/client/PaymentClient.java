package com.avik.microservices.order_service.client;

import com.avik.microservices.order_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse pay(
            @RequestParam UUID orderId,
            @RequestParam String status
    );
}
