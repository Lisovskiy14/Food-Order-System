package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderItem;
import org.example.foodordersystem.dto.order.ManageOrderItemRequestDto;
import org.example.foodordersystem.dto.order.OrderRequestDto;
import org.example.foodordersystem.service.CustomerService;
import org.example.foodordersystem.service.ItemService;
import org.example.foodordersystem.service.OrderService;
import org.example.foodordersystem.service.exception.OrderNotFoundException;
import org.example.foodordersystem.service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ItemService itemService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    @Override
    public Order getOrderById(UUID id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException(id.toString());
        }
        return order;
    }

    @Override
    public Order saveOrder(OrderRequestDto orderRequestDto) {
        UUID id = customerService.getCustomerById(UUID.fromString(
                orderRequestDto.getCustomerId())).getId();

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .customerId(id)
                .build();

        Set<OrderItem> orderItems = orderRequestDto.getOrderItems().stream()
                        .map(requestOrderItem -> OrderItem.builder()
                                .id(UUID.randomUUID())
                                .orderId(order.getId())
                                .itemId(itemService.getItemById(
                                        UUID.fromString(requestOrderItem.getItemId()))
                                        .getId())
                                .quantity(requestOrderItem.getQuantity())
                                .build())
                        .collect(Collectors.toSet());

        Optional<Double> totalPrice = orderItems.stream()
                        .map(orderItem -> itemService.getItemById(orderItem.getItemId()).getPrice()
                                * orderItem.getQuantity())
                        .reduce(Double::sum);

        order.setOrderItems(orderItems);
        totalPrice.ifPresent(order::setTotalPrice);

        return orderRepository.saveOrder(order);
    }

    @Override
    public Order addItemToOrder(ManageOrderItemRequestDto manageOrderItemRequestDto) {
        Order order = getOrderById(UUID.fromString(
                manageOrderItemRequestDto.getOrderId()));
        Set<OrderItem> orderItems = order.getOrderItems();

        Item item = itemService.getItemById(
                UUID.fromString(manageOrderItemRequestDto.getItemId()));

        orderItems.add(OrderItem.builder()
                .orderId(order.getId())
                .itemId(item.getId())
                .quantity(manageOrderItemRequestDto.getQuantity())
                .build());
        order.setOrderItems(orderItems);

        order.setTotalPrice(order.getTotalPrice() + (item.getPrice() * manageOrderItemRequestDto.getQuantity()));

        return orderRepository.saveOrder(order);
    }

    @Override
    public Order removeItemFromOrder(ManageOrderItemRequestDto manageOrderItemRequestDto) {
        Order order = getOrderById(UUID.fromString(
                manageOrderItemRequestDto.getOrderId()));
        Set<OrderItem> orderItems = order.getOrderItems();

        Item item = itemService.getItemById(UUID.fromString(manageOrderItemRequestDto.getItemId()));

        order.setTotalPrice(order.getTotalPrice() - orderItems.stream()
                .filter(orderItem -> orderItem.getItemId().toString()
                        .equals(manageOrderItemRequestDto.getItemId()))
                .findFirst()
                .map(orderItem -> orderItem.getQuantity()
                        * itemService.getItemById(orderItem.getItemId()).getPrice())
                .get()
        );

        orderItems.removeIf(orderItem -> orderItem
                .getItemId().toString()
                .equals(manageOrderItemRequestDto.getItemId()));
        order.setOrderItems(orderItems);

        return orderRepository.saveOrder(order);
    }

    @Override
    public void deleteOrderById(UUID id) {
        orderRepository.deleteOrderById(id);
    }
}
