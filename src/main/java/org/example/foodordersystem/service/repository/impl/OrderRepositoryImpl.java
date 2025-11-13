package org.example.foodordersystem.service.repository.impl;

import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.service.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    private final ConcurrentHashMap<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public List<Order> getAllOrders() {
        return orders.values().stream().toList();
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }

    @Override
    public Order getOrderById(UUID id) {
        return orders.get(id);
    }

    @Override
    public Order saveOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public void deleteOrderById(UUID id) {
        orders.remove(id);
    }
}
