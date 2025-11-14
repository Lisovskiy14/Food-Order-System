package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.service.CustomerService;
import org.example.foodordersystem.service.exception.CustomerNotFoundException;
import org.example.foodordersystem.service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id.toString());
        }
        return customer;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.saveCustomer(customer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerRepository.deleteCustomerById(id);
    }
}
