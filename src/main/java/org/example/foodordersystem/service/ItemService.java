package org.example.foodordersystem.service;

import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.dto.item.ItemUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(UUID id);
    Item saveItem(Item item);
    Item updateItem(ItemUpdateRequestDto itemUpdateRequestDto);
    void deleteItemById(UUID id);
}
