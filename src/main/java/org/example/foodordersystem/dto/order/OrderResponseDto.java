package org.example.foodordersystem.dto.order;

import lombok.Value;

import java.util.List;

@Value
public class OrderResponseDto {
    String id;
    String customerId;
    double totalPrice;
    List<OrderItemResponseDto> orderItems;
}
