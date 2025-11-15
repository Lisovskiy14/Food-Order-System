package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> getOrdersByCustomerId(UUID customerId);
}
