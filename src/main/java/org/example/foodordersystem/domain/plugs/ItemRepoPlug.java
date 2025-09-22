package org.example.foodordersystem.domain.plugs;

import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepoPlug implements ItemRepository {

    private final List<Item> items = new ArrayList<>();

    {
        items.add(new Item("Вареники", 150));
        items.add(new Item("Борщ", 120));
        items.add(new Item("Сирники", 89));
        items.add(new Item("Пампушки", 20));
        items.add(new Item("Лазанья", 210));
    }

    @Override
    public List<Item> getAllItems() {
        return items;
    }

    @Override
    public void saveItem(Item item) {
        items.add(item);
    }

    @Override
    public void deleteItem(Item item) {
        items.remove(item);
    }
}
