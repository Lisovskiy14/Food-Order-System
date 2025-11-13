package org.example.foodordersystem.dto.customer;

import lombok.Value;

import java.util.List;

@Value
public class CustomerListResponseDto {
    List<CustomerResponseDto> customers;
}
