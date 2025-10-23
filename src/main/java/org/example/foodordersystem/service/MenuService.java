package org.example.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final ItemRepository itemRepository;

    public List<Item> getMenu() {
        return itemRepository.getAllItems();
    }

    public void addItem(Item item) {
        item.setId(UUID.randomUUID());
        itemRepository.saveItem(item);
    }

    public void deleteItem(UUID id) {
        itemRepository.deleteItem(id);
    }
}
