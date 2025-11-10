package org.example.foodordersystem.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.dto.order.ManageOrderItemRequestDto;
import org.example.foodordersystem.dto.order.OrderListResponseDto;
import org.example.foodordersystem.dto.order.OrderRequestDto;
import org.example.foodordersystem.service.OrderService;
import org.example.foodordersystem.web.mapper.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OrderListResponseDto(
                        orderService.getAllOrders().stream()
                                .map(orderMapper::toOrderResponseDto)
                                .toList()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMapper.toOrderResponseDto(
                        orderService.getOrderById(orderId)));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<Object> getOrdersByCustomerId(@PathVariable UUID customerId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OrderListResponseDto(
                        orderService.getOrdersByCustomerId(customerId).stream()
                                .map(orderMapper::toOrderResponseDto)
                                .toList()));
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMapper.toOrderResponseDto(
                        orderService.saveOrder(orderRequestDto)));
    }

    @PatchMapping("/add-item")
    public ResponseEntity<Object> addItemToOrder(@Valid @RequestBody ManageOrderItemRequestDto manageOrderItemRequestDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMapper.toOrderResponseDto(
                        orderService.addItemToOrder(manageOrderItemRequestDto)));
    }

    @PatchMapping("/remove-item")
    public ResponseEntity<Object> removeItemFromOrder(@Valid @RequestBody ManageOrderItemRequestDto manageOrderItemRequestDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMapper.toOrderResponseDto(
                        orderService.removeItemFromOrder(manageOrderItemRequestDto)));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Object> deleteOrderById(@PathVariable UUID orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
}
