package org.example.foodordersystem.dto.item;

import lombok.Value;

@Value
public class ItemResponseDto {
    String id;
    String name;
    String description;
    double price;
}
