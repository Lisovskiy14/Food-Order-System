package org.example.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Customer customer) {
        Order order = Order.builder()
                .customer(customer)
                .items(new ArrayList<>())
                .totalPrice(0)
                .build();

        return order;
    }

    public Order addItemToOrder(Order order, Item item) {
        return order.addItem(item);
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

    public void completeOrder(Order order) {
        orderRepository.deleteOrder(order);
    }
}
