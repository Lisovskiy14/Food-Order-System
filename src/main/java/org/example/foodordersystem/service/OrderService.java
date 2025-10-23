package org.example.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public Order createOrder(Customer customer) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .items(new ArrayList<>())
                .totalPrice(0)
                .build();
        orderRepository.saveOrder(order);
        return order;
    }

    public Order addItemToOrder(UUID orderId, UUID itemId) {
        return orderRepository.getOrder(orderId)
                .addItem(itemRepository.getItem(itemId));
    }

    public void sendOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        orderRepository.saveOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public void completeOrder(UUID id) {
        orderRepository.deleteOrder(id);
    }
}
