package org.example.foodordersystem.domain.plugs;

import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepPlug implements OrderRepository {

    private final Map<UUID, Order> orders = new HashMap<>();

    @Override
    public void saveOrder(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Order getOrder(UUID id) {
        return orders.get(id);
    }

    @Override
    public void deleteOrder(UUID id) {
        orders.remove(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return List.copyOf(orders.values());
    }
}
