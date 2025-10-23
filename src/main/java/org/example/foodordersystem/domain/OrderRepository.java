package org.example.foodordersystem.domain;


import java.util.List;
import java.util.UUID;

public interface OrderRepository {

    void saveOrder(Order order);
    Order getOrder(UUID id);
    void deleteOrder(UUID id);
    List<Order> getAllOrders();
}
