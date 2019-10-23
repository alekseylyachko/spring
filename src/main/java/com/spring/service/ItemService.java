package com.spring.service;

import com.spring.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item save(Item item);

    Item findByName(String name);

    Item findById(UUID id);

    List<Item> getAll();

    void deleteById(UUID id);
}
