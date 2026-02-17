package com.avik.microservices.order_service.controller;

import com.avik.microservices.order_service.entity.Order;
import com.avik.microservices.order_service.exception.BadRequestException;
import com.avik.microservices.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam String sku,
            @RequestParam Integer quantity,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                orderService.placeOrder(
                        sku,
                        quantity,
                        UUID.fromString(userId),
                        email
                )
        );
    }


    @PostMapping("/{orderId}/pay")
    public Order payOrder(
            @PathVariable UUID orderId,
            @RequestParam String status,
            @RequestHeader("X-User-Id") String userId) {

        return orderService.payOrder(
                orderId,
                UUID.fromString(userId),
                status
        );
    }



    @GetMapping("/my-orders")
    public List<Order> getMyOrders(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        if (userId == null) {
            throw new BadRequestException("User ID header missing");
        }
        System.out.println("UserId Header: " + userId);


        return orderService.getMyOrders(UUID.fromString(userId));
    }




    @GetMapping("/{orderId}")
    public Order getOrder(
            @PathVariable UUID orderId,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        return orderService.getOrderById(
                orderId,
                UUID.fromString(userId),
                role
        );
    }
}


