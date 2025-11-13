package org.example.foodordersystem.service;

import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.dto.order.ManageOrderItemRequestDto;
import org.example.foodordersystem.dto.order.OrderRequestDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByCustomerId(UUID customerId);
    Order getOrderById(UUID id);
    Order saveOrder(OrderRequestDto orderRequestDto);
    Order addItemToOrder(ManageOrderItemRequestDto manageOrderItemRequestDto);
    Order removeItemFromOrder(ManageOrderItemRequestDto manageOrderItemRequestDto);
    void deleteOrderById(UUID id);
}
