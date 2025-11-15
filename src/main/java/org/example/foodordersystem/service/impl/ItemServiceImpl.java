package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.dto.item.ItemUpdateRequestDto;
import org.example.foodordersystem.service.ItemService;
import org.example.foodordersystem.service.exception.ItemNameAlreadyExistsException;
import org.example.foodordersystem.service.exception.ItemNotFoundException;
import org.example.foodordersystem.service.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(UUID id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id.toString()));
    }

    @Override
    public Item createItem(Item item) {
        if (itemRepository.existsByName(item.getName())) {
            throw new ItemNameAlreadyExistsException(item.getName());
        }
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(ItemUpdateRequestDto itemUpdateRequestDto) {
        Item item = getItemById(UUID.fromString(itemUpdateRequestDto.getId()));

        if (itemUpdateRequestDto.getDescription() != null) {
            item.setDescription(itemUpdateRequestDto.getDescription());
        }
        if (itemUpdateRequestDto.getPrice() != null) {
            item.setPrice(itemUpdateRequestDto.getPrice());
        }

        return itemRepository.save(item);
    }

    @Override
    public void deleteItemById(UUID id) {
        itemRepository.deleteById(id);
    }
}
