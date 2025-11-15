package org.example.foodordersystem.web.mapper;

import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderItem;
import org.example.foodordersystem.dto.order.OrderItemResponseDto;
import org.example.foodordersystem.dto.order.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "customer.id", target = "customerId")
    OrderResponseDto toOrderResponseDto(Order order);
    @Mapping(source = "item.id", target = "itemId")
    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);
}
