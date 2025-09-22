package org.example.foodordersystem.domain;


import java.util.List;

public interface OrderRepository {

    void saveOrder(Order order);
    Order getOrder(Customer customer);
    void deleteOrder(Order order);
    List<Order> getAllOrders();
}
