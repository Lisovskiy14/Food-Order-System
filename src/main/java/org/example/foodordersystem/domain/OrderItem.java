package org.example.foodordersystem.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderItem {
    private UUID id;
    private UUID orderId;
    private UUID itemId;
    private int quantity;
}
