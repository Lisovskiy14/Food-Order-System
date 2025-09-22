package org.example.foodordersystem.domain.plugs;

import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepPlug implements OrderRepository {

    private final Map<String, Order> orders = new HashMap<>();

    {
        Customer customer = new Customer("Іван", "Іванович");
        orders.put(customer.getFirstName() + customer.getLastName(),
                Order.builder()
                        .customer(customer)
                        .items(new ArrayList<>())
                        .totalPrice(0)
                .build()
        );

        customer = new Customer("Сергій", "Степанович");
        orders.put(customer.getFirstName() + customer.getLastName(),
                Order.builder()
                        .customer(customer)
                        .items(new ArrayList<>())
                        .totalPrice(0)
                        .build()
        );
    }

    @Override
    public void saveOrder(Order order) {
        String key = getKey(order.getCustomer());
        orders.put(key, order);
    }

    @Override
    public Order getOrder(Customer customer) {
        String key = getKey(customer);
        if (orders.containsKey(key)) {
            return orders.get(key);
        }
        return null;
    }

    @Override
    public void deleteOrder(Order order) {
        orders.remove(getKey(order.getCustomer()));
    }

    @Override
    public List<Order> getAllOrders() {
        return List.copyOf(orders.values());
    }

    private String getKey(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
