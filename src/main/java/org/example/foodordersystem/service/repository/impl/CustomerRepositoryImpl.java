package org.example.foodordersystem.service.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.service.repository.CustomerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JdbcClient jdbcClient;

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        return jdbcClient.sql(sql)
                .query(Customer.class)
                .list();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try {
            return jdbcClient.sql(sql)
                    .param(id)
                    .query(Customer.class)
                    .single();
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param(customer.getFirstName())
                .param(customer.getLastName())
                .update(keyHolder, "id");

        customer.setId(keyHolder.getKeyAs(UUID.class));
        return customer;
    }

    @Override
    public void deleteCustomerById(UUID id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
