package org.example.foodordersystem.service.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderItem;
import org.example.foodordersystem.service.exception.OrderNotFoundException;
import org.example.foodordersystem.service.repository.OrderItemRepository;
import org.example.foodordersystem.service.repository.OrderRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OrderItemRepository orderItemRepository;

    private final RowMapper<Order> orderRowMapper = (ResultSet rs, int rowNum) -> {
        return Order.builder()
                .id(rs.getObject("id", UUID.class))
                .customerId(rs.getObject("customer_id", UUID.class))
                .orderItems(new HashSet<>())
                .totalPrice(rs.getDouble("total_price"))
                .build();
    };

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";

        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper);
        Map<UUID, Order> ordersMap = new HashMap<>();
        orders.forEach((order) -> ordersMap.put(order.getId(), order));

        Set<OrderItem> orderItems = orderItemRepository.getAllOrderItems();
        orderItems.forEach((orderItem) -> ordersMap
                .get(orderItem.getOrderId())
                .getOrderItems()
                .add(orderItem)
        );

        return orders;
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";

        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper, customerId);
        Map<UUID, Order> ordersMap = new HashMap<>();
        orders.forEach((order) -> ordersMap.put(order.getId(), order));

        Set<OrderItem> orderItems = orderItemRepository.getAllOrderItemsByOrderIdSet(ordersMap.keySet());
        orderItems.forEach((orderItem) -> ordersMap
                .get(orderItem.getOrderId())
                .getOrderItems()
                .add(orderItem)
        );

        return orders;
    }

    @Override
    public Order getOrderById(UUID id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            Order order = jdbcTemplate.queryForObject(sql, orderRowMapper, id);
            Set<OrderItem> orderItems = order.getOrderItems();
            orderItems.addAll(orderItemRepository.getAllOrderItemsByOrderId(id));
            return order;
        } catch (EmptyResultDataAccessException ex) {
            throw new OrderNotFoundException(id.toString());
        }
    }

    @Override
    public Order saveOrder(Order order) {
        String sql = "INSERT INTO orders (id, customer_id, total_price) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, order.getId());
            ps.setObject(2, order.getCustomerId());
            ps.setDouble(3, order.getTotalPrice());
            return ps;
        });

        return order;
    }

    @Override
    public Order setOrderTotalPrice(Order order) {
        String sql = """
                UPDATE orders
                SET total_price = (
                    SELECT SUM(i.price * oi.quantity) AS total_price
                    FROM order_items oi
                    JOIN items i
                        ON i.id = oi.item_id
                    WHERE oi.order_id = ?
                )
                WHERE id = ?
                RETURNING total_price
                """;

        double totalPrice = jdbcTemplate.queryForObject(sql, Double.class, order.getId(), order.getId());
        order.setTotalPrice(totalPrice);

        return order;
    }

    @Override
    public void deleteOrderById(UUID id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private double countTotalPrice(UUID orderId) {
        String sql = """
                SELECT SUM(i.price * oi.quantity) AS total_price
                FROM order_items oi
                JOIN items i
                    ON i.id = oi.item_id
                WHERE oi.order_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, Double.class, orderId);
    }
}
