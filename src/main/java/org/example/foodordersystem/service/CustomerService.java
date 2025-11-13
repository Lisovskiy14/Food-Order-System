package org.example.foodordersystem.service;

import org.example.foodordersystem.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id);
    Customer saveCustomer(Customer customer);
    void deleteCustomerById(UUID id);
}
