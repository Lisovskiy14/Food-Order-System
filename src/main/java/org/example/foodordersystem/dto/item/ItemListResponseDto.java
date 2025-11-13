package org.example.foodordersystem.dto.item;

import lombok.Value;

import java.util.List;

@Value
public class ItemListResponseDto {
    List<ItemResponseDto> items;
}
