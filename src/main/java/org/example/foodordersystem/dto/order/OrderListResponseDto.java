package org.example.foodordersystem.dto.order;

import lombok.Value;

import java.util.List;

@Value
public class OrderListResponseDto {
    List<OrderResponseDto> orders;
}
