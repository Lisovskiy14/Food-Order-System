package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository {
    List<Order> getAllOrders();
    List<Order> getOrdersByCustomerId(UUID customerId);
    Order getOrderById(UUID id);
    Order saveOrder(Order order);
    void deleteOrderById(UUID id);

}
