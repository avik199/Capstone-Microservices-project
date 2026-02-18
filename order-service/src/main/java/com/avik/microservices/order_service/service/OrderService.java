package com.avik.microservices.order_service.service;
import com.avik.microservices.order_service.client.CatalogueClient;
import com.avik.microservices.order_service.client.InventoryClient;
import com.avik.microservices.order_service.client.PaymentClient;
import com.avik.microservices.order_service.dto.InventoryResponse;
import com.avik.microservices.order_service.dto.PaymentResponse;
import com.avik.microservices.order_service.dto.ProductResponse;
import com.avik.microservices.order_service.entity.Order;
import com.avik.microservices.order_service.entity.OrderStatus;
import com.avik.microservices.order_service.exception.BadRequestException;
import com.avik.microservices.order_service.exception.ResourceNotFoundException;
import com.avik.microservices.order_service.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CatalogueClient catalogueClient;
    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public Order placeOrder(String sku,
                            Integer quantity,
                            UUID userId,
                            String email) {

        ProductResponse product;
        try {
            product = catalogueClient.getProduct(sku);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Product not found");
        }

        InventoryResponse stock;
        try {
            stock = inventoryClient.checkStock(sku);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory not found");
        }

        if (stock.getAvailableStock() == null ||
                stock.getAvailableStock() < quantity) {
            throw new BadRequestException("Insufficient stock");
        }

        inventoryClient.reserveStock(sku, quantity);

        Order order = Order.builder()
                .sku(sku)
                .quantity(quantity)
//                .price(product.getPrice())
                .userId(userId)
                .userEmail(email)
                .status(OrderStatus.PENDING_PAYMENT)
                .build();

        return orderRepository.save(order);
    }


    @Transactional
    public Order payOrder(UUID orderId,
                          UUID userId,
                          String paymentStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));


        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("You cannot pay for another user's order");
        }

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new BadRequestException("Order cannot be paid");
        }

        PaymentResponse payment =
                paymentClient.pay(orderId, paymentStatus);

        if ("SUCCESS".equals(payment.getStatus())) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            inventoryClient.releaseStock(order.getSku(), order.getQuantity());
            order.setStatus(OrderStatus.CANCELLED);
        }

        return order;
    }

    public List<Order> getMyOrders(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(UUID orderId, UUID userId, String role) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // ADMIN can see all
        if ("ADMIN".equals(role)) {
            return order;
        }

        // USER can see only their own
        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("Access denied");
        }

        return order;
    }
}
