package org.example.foodordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        Item savedItem = itemRepository.save(item);
        log.info("New Item with id '{}' has been created'", savedItem.getId());
        return savedItem;
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

        Item savedItem = itemRepository.save(item);
        log.info("Item with id '{}' has been updated'", savedItem.getId());
        return savedItem;
    }

    @Override
    public void deleteItemById(UUID id) {
        itemRepository.deleteById(id);
        log.info("Item with id '{}' has been deleted", id);
    }
}
