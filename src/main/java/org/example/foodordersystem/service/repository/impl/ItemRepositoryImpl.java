package org.example.foodordersystem.service.repository.impl;

import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.service.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final ConcurrentHashMap<UUID, Item> items = new ConcurrentHashMap<>();

    {
        UUID id1 = UUID.randomUUID();
        items.put(id1, new Item(id1, "Вареники", "Дуже смачні вареники з картоплею", 150));
        UUID id2 = UUID.randomUUID();
        items.put(id2, new Item(id2, "Борщ", "Дуже смачний борщ зі сметанкою", 120));
        UUID id3 = UUID.randomUUID();
        items.put(id3, new Item(id3, "Сирники", "Справжні домашні сирники", 89));
        UUID id4 = UUID.randomUUID();
        items.put(id4, new Item(id4, "Пампушки", "Дуже смачні пампушки з часником", 20));
        UUID id5 = UUID.randomUUID();
        items.put(id5, new Item(id5, "Лазанья", "Дуже смачна італійська страва", 210));
    }


    @Override
    public List<Item> getAllItems() {
        return items.values().stream().toList();
    }

    @Override
    public Item getItemById(UUID id) {
        return items.get(id);
    }

    @Override
    public Item saveItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteItemById(UUID id) {
        items.remove(id);
    }
}
