package com.shoppingplatform.price;

import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    private final Map<String, Item> items;

    public ItemRepository() {
        this.items = new HashMap<>();
    }

    public void save(Item item) {
        items.put(item.getName(), item);
    }

    public boolean contains(String itemName) {
        return items.containsKey(itemName);
    }

    public Item findByName(String itemName) {
        return items.get(itemName);
    }
}
