package org.example.foodordersystem.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {

    private final Customer customer;
    private final List<Item> items;
    private double totalPrice;

    public Order addItem(Item item) {
        items.add(item);
        totalPrice += item.getPrice();
        return this;
    }
}
