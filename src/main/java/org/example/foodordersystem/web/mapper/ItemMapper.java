package org.example.foodordersystem.web.mapper;

import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.dto.item.ItemRequestDto;
import org.example.foodordersystem.dto.item.ItemResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toItem(ItemRequestDto itemRequestDto);
    ItemResponseDto toItemResponseDto(Item item);
}
