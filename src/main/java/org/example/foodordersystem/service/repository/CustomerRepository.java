package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository {
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id);
    Customer saveCustomer(Customer customer);
    void deleteCustomerById(UUID id);
}
