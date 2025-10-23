package org.example.foodordersystem.domain;

import java.util.List;
import java.util.UUID;

public interface ItemRepository {

    Item getItem(UUID id);
    List<Item> getAllItems();
    void saveItem(Item item);
    void deleteItem(UUID id);
}
