package org.example.foodordersystem.service.repository.impl;

import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.service.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final ConcurrentHashMap<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public List<Customer> getAllCustomers() {
        return customers.values().stream().toList();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customers.remove(id);
    }
}
