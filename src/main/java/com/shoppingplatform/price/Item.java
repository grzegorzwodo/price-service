package com.shoppingplatform.price;

public class Item {
    private final String name;
    private final int price; // Price per unit in cents
    private final int specialQuantity;
    private final int specialPrice;

    public Item(String name, int price, int specialQuantity, int specialPrice) {
        this.name = name;
        this.price = price;
        this.specialQuantity = specialQuantity;
        this.specialPrice = specialPrice;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getSpecialQuantity() {
        return specialQuantity;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }
}
