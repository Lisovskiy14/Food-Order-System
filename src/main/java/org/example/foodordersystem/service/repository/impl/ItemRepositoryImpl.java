package org.example.foodordersystem.service.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.service.repository.ItemRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final JdbcClient jdbcClient;

    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM items";
        return jdbcClient.sql(sql)
                .query(Item.class)
                .list();
    }

    @Override
    public Item getItemById(UUID id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try {
            return jdbcClient.sql(sql)
                    .param(id)
                    .query(Item.class)
                    .single();
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Item saveItem(Item item) {
        String sql = "INSERT INTO items (name, description, price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param(item.getName())
                .param(item.getDescription())
                .param(item.getPrice())
                .update(keyHolder, "id");

        item.setId(keyHolder.getKeyAs(UUID.class));
        return item;
    }

    @Override
    public void deleteItemById(UUID id) {
        String sql = "DELETE FROM items WHERE id = ?";
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
