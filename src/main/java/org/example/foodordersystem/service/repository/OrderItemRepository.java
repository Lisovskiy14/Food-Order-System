package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.OrderItem;

import java.util.Set;
import java.util.UUID;

public interface OrderItemRepository {
    Set<OrderItem> getAllOrderItems();
    Set<OrderItem> getAllOrderItemsByOrderIdSet(Set<UUID> orderIds);
    Set<OrderItem> getAllOrderItemsByOrderId(UUID orderId);
    Set<OrderItem> saveOrderItems(Set<OrderItem> orderItems);
}
