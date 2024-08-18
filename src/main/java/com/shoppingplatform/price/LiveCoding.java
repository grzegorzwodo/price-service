package com.shoppingplatform.price;

public class LiveCoding {

    public static void main(String[] args) {
        Item itemA = new Item("A", 40, 3, 70);
        Item itemB = new Item("B", 10, 2, 15);
        Item itemC = new Item("C", 30, 4, 60);
        Item itemD = new Item("D", 25, 2, 40);

        // Initialize the checkout system
        Checkout checkout = new Checkout();

        // Add items to the system
        checkout.addItem(itemA);
        checkout.addItem(itemB);
        checkout.addItem(itemC);
        checkout.addItem(itemD);

        // Scan items
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("D");
        checkout.scan("D");

        // Print the receipt
        System.out.println(checkout.generateReceipt());
    }
}
