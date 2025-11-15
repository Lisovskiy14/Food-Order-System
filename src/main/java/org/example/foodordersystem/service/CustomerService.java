package org.example.foodordersystem.service;

import org.example.foodordersystem.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id);
    Customer createCustomer(Customer customer);
    void deleteCustomerById(UUID id);
}
