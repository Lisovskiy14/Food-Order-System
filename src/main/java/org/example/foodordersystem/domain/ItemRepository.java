package org.example.foodordersystem.domain;

import java.util.List;

public interface ItemRepository {

    List<Item> getAllItems();
    void saveItem(Item item);
    void deleteItem(Item item);
}
