package com.avik.microservices.payment_service.Repository;

import com.avik.microservices.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
