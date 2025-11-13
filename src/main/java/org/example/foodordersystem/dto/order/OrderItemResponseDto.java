package org.example.foodordersystem.dto.order;

import lombok.Value;

@Value
public class OrderItemResponseDto {
    String itemId;
    int quantity;
}
