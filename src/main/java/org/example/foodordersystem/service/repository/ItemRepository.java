package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(UUID id);
    Item saveItem(Item item);
    void deleteItemById(UUID id);
}
