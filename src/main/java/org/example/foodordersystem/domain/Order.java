package org.example.foodordersystem.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Order {

    private UUID id;
    private final Customer customer;
    private final List<Item> items;
    private double totalPrice;

    public Order addItem(Item item) {
        items.add(item);
        totalPrice += item.getPrice();
        return this;
    }
}
