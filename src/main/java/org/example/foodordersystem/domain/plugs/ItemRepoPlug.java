package org.example.foodordersystem.domain.plugs;

import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepoPlug implements ItemRepository {

    private final Map<UUID, Item> items = new HashMap<>();

    {
        UUID id1 = UUID.randomUUID();
        items.put(id1, new Item(id1, "Вареники", 150));
        UUID id2 = UUID.randomUUID();
        items.put(id2, new Item(id2, "Борщ", 120));
        UUID id3 = UUID.randomUUID();
        items.put(id3, new Item(id3, "Сирники", 89));
        UUID id4 = UUID.randomUUID();
        items.put(id4, new Item(id4, "Пампушки", 20));
        UUID id5 = UUID.randomUUID();
        items.put(id5, new Item(id5, "Лазанья", 210));
    }

    @Override
    public Item getItem(UUID id) {
        return items.get(id);
    }

    @Override
    public List<Item> getAllItems() {
        return items.values().stream().toList();
    }

    @Override
    public void saveItem(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void deleteItem(UUID id) {
        items.remove(id);
    }
}
