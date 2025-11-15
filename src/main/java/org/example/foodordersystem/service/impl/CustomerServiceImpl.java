package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.service.CustomerService;
import org.example.foodordersystem.service.exception.CustomerNotFoundException;
import org.example.foodordersystem.service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.toString()));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        log.info("New Customer with id '{}' has been created'", savedCustomer.getId());
        return savedCustomer;
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerRepository.deleteById(id);
        log.info("Customer with id '{}' has been deleted", id);
    }
}
