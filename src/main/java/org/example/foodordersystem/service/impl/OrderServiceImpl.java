package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderItem;
import org.example.foodordersystem.dto.order.ManageOrderItemRequestDto;
import org.example.foodordersystem.dto.order.OrderItemRequestDto;
import org.example.foodordersystem.dto.order.OrderRequestDto;
import org.example.foodordersystem.service.OrderService;
import org.example.foodordersystem.service.exception.CustomerNotFoundException;
import org.example.foodordersystem.service.exception.ItemNotFoundException;
import org.example.foodordersystem.service.exception.OrderNotFoundException;
import org.example.foodordersystem.service.repository.CustomerRepository;
import org.example.foodordersystem.service.repository.ItemRepository;
import org.example.foodordersystem.service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
        UUID customerId = UUID.fromString(orderRequestDto.getCustomerId());
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId.toString());
        }

        Set<UUID> itemIds = orderRequestDto.getOrderItems().stream()
                .map(OrderItemRequestDto::getItemId)
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        Map<UUID, Item> itemsMap = itemRepository.findAllByIdSet(itemIds).stream()
                .collect(Collectors.toMap(Item::getId, item -> item));

        Order order = Order.builder()
                .customer(customerRepository.getReferenceById(customerId))
                .orderItems(Set.of())
                .build();

        Set<OrderItem> orderItems = orderRequestDto.getOrderItems().stream()
                .map(requestOrderItem -> {
                    UUID itemId = UUID.fromString(requestOrderItem.getItemId());
                    if (!itemsMap.containsKey(itemId)) {
                        throw new ItemNotFoundException(itemId.toString());
                    }
                    return OrderItem.builder()
                            .order(order)
                            .item(itemsMap.get(itemId))
                            .quantity(requestOrderItem.getQuantity())
                            .build();
                })
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        BigDecimal totalPrice = orderItems.stream()
                .map(orderItem -> orderItem.getItem().getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);

        log.info("New Order with id '{}' has been created'", order.getId());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order addItemToOrder(ManageOrderItemRequestDto manageOrderItemRequestDto) {
        Order order = getOrderById(UUID.fromString(
                manageOrderItemRequestDto.getOrderId()));

        UUID itemId = UUID.fromString(manageOrderItemRequestDto.getItemId());
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId.toString()));


        Set<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(OrderItem.builder()
                .order(order)
                .item(item)
                .quantity(manageOrderItemRequestDto.getQuantity())
                .build());

        order.setOrderItems(orderItems);
        order.setTotalPrice(order.getTotalPrice()
                .add(item.getPrice().multiply(
                        BigDecimal.valueOf(manageOrderItemRequestDto.getQuantity())))
        );

        log.info("New OrderItem has been added to Order with id '{}'", order.getId());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order removeItemFromOrder(ManageOrderItemRequestDto manageOrderItemRequestDto) {
        Order order = getOrderById(UUID.fromString(
                manageOrderItemRequestDto.getOrderId()));

        UUID itemId = UUID.fromString(manageOrderItemRequestDto.getItemId());

        Set<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.stream()
                .filter(oi -> oi.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId.toString()));

        BigDecimal newTotalPrice = order.getTotalPrice().subtract(
                orderItem.getItem().getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
        );
        order.setTotalPrice(newTotalPrice);

        orderItems.remove(orderItem);

        log.info("OrderItem with id '{}' has been removed from Order with id '{}'",
                orderItem.getId(),
                order.getId());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }
}
