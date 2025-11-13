package org.example.foodordersystem.web.mapper;

import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.dto.customer.CustomerRequestDto;
import org.example.foodordersystem.dto.customer.CustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequestDto customerRequestDto);
    CustomerResponseDto toCustomerResponseDto(Customer customer);
}
