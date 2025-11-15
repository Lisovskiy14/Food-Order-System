package org.example.foodordersystem.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.dto.customer.CustomerListResponseDto;
import org.example.foodordersystem.dto.customer.CustomerRequestDto;
import org.example.foodordersystem.service.CustomerService;
import org.example.foodordersystem.web.mapper.CustomerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerListResponseDto(
                        customerService.getAllCustomers().stream()
                                .map(customerMapper::toCustomerResponseDto)
                                .toList()));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable UUID customerId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerMapper.toCustomerResponseDto(
                        customerService.getCustomerById(customerId)));
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = customerMapper.toCustomer(customerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerMapper.toCustomerResponseDto(
                        customerService.createCustomer(customer)));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
