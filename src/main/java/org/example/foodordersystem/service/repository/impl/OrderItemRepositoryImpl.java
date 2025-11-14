package org.example.foodordersystem.service.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.OrderItem;
import org.example.foodordersystem.service.repository.OrderItemRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OrderItem> orderItemRowMapper = (ResultSet rs, int rowNum) -> {
        return OrderItem.builder()
                .id(rs.getObject("id", UUID.class))
                .orderId(rs.getObject("order_id", UUID.class))
                .itemId(rs.getObject("item_id", UUID.class))
                .quantity(rs.getInt("quantity"))
                .build();
    };

    @Override
    public Set<OrderItem> getAllOrderItems() {
        String sql = "SELECT * FROM order_items";
        return new HashSet<>(jdbcTemplate.query(sql, orderItemRowMapper));
    }

    @Override
    public Set<OrderItem> getAllOrderItemsByOrderIdSet(Set<UUID> orderIds) {
        String joinedIds = String.join(",", orderIds.stream()
                .map(id -> "'" + id + "'")
                .toArray(String[]::new)
        );

        String sql = "SELECT * FROM order_items WHERE order_id IN (" + joinedIds + ")";

        return new HashSet<>(jdbcTemplate.query(sql, orderItemRowMapper));
    }

    @Override
    public Set<OrderItem> getAllOrderItemsByOrderId(UUID orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, orderItemRowMapper, orderId));
    }

    @Override
    public Set<OrderItem> saveOrderItems(Set<OrderItem> orderItems) {
        String sql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES ";
        List<String> values = new ArrayList<>();

        orderItems.forEach(orderItem -> {
            values.add(String.format("('%s', '%s', '%d')",
                    orderItem.getOrderId(),
                    orderItem.getItemId(),
                    orderItem.getQuantity()
            ));
        });

        sql += String.join(", ", values);
        jdbcTemplate.update(sql);

        return orderItems;
    }
}
