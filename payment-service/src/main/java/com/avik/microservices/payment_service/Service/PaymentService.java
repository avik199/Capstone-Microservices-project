package com.avik.microservices.payment_service.Service;

import com.avik.microservices.payment_service.Repository.PaymentRepository;
import com.avik.microservices.payment_service.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment processPayment(UUID orderId, String status) {

        log.info("Processing payment for OrderId: {}", orderId);
        // Fake payment success logic
        if (!status.equalsIgnoreCase("SUCCESS") &&
                !status.equalsIgnoreCase("FAILED")) {
            log.error("Invalid payment status received: {}", status);
            throw new IllegalArgumentException("Status must be SUCCESS or FAILED");
        }

        Payment payment = Payment.builder()
                .orderId(orderId)
                .status(status.toUpperCase())
                .build();
        Payment savedPayment = paymentRepository.save(payment);

        log.info("Payment saved successfully | PaymentId: {} | Status: {}",
                savedPayment.getId(), savedPayment.getStatus());

        return savedPayment;
    }
}
