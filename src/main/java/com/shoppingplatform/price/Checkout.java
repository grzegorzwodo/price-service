package com.shoppingplatform.price;

import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private final ItemRepository itemRepository;
    private final Map<String, Integer> cart;

    public Checkout() {
        itemRepository = new ItemRepository();
        cart = new HashMap<>();
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void scan(String itemName) {
        if (!itemRepository.contains(itemName)) {
            throw new IllegalArgumentException("Item not found: " + itemName);
        }
        cart.put(itemName, cart.getOrDefault(itemName, 0) + 1);
    }

    public int calculateTotal() {
        int total = 0;

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            Item item = itemRepository.findByName(itemName);

            // Calculate price based on special pricing
            if (item.getSpecialQuantity() > 0 && quantity >= item.getSpecialQuantity()) {
                int specialBundles = quantity / item.getSpecialQuantity();
                int remainingItems = quantity % item.getSpecialQuantity();
                total += specialBundles * item.getSpecialPrice() + remainingItems * item.getPrice();
            } else {
                total += quantity * item.getPrice();
            }
        }

        return total;
    }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder("Receipt:\n");

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            Item item = itemRepository.findByName(itemName);
            int itemTotalPrice;

            if (item.getSpecialQuantity() > 0 && quantity >= item.getSpecialQuantity()) {
                int specialBundles = quantity / item.getSpecialQuantity();
                int remainingItems = quantity % item.getSpecialQuantity();
                itemTotalPrice = specialBundles * item.getSpecialPrice() + remainingItems * item.getPrice();
            } else {
                itemTotalPrice = quantity * item.getPrice();
            }

            receipt.append(itemName)
                .append(" x ")
                .append(quantity)
                .append(" = ")
                .append(itemTotalPrice)
                .append(" cents\n");
        }

        receipt.append("Total: ").append(calculateTotal()).append(" cents\n");
        return receipt.toString();
    }
}