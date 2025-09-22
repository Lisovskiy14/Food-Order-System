package org.example.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final ItemRepository itemRepository;

    public List<Item> getMenu() {
        return itemRepository.getAllItems();
    }

    public void addItem(Item item) {
        itemRepository.saveItem(item);
    }

    public void deleteItem(Item item) {
        itemRepository.deleteItem(item);
    }
}
