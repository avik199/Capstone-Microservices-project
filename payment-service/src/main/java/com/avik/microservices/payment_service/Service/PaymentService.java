package com.avik.microservices.payment_service.Service;

import com.avik.microservices.payment_service.Repository.PaymentRepository;
import com.avik.microservices.payment_service.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment processPayment(UUID orderId, String status) {

        // Fake payment success logic
        if (!status.equalsIgnoreCase("SUCCESS") &&
                !status.equalsIgnoreCase("FAILED")) {
            throw new IllegalArgumentException("Status must be SUCCESS or FAILED");
        }

        Payment payment = Payment.builder()
                .orderId(orderId)
                .status(status.toUpperCase())
                .build();

        return paymentRepository.save(payment);
    }
}
