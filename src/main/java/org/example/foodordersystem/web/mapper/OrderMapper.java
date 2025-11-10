package org.example.foodordersystem.web.mapper;

import org.example.foodordersystem.dto.order.OrderRequestDto;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.dto.order.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto toOrderResponseDto(Order order);
}
