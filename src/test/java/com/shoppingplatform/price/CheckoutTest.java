package com.shoppingplatform.price;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutTest {

    private Checkout checkout;

    @BeforeEach
    public void setup() {
        // Initialize the checkout system before each test
        checkout = new Checkout();

        // Add items to the system
        checkout.addItem(new Item("A", 40, 3, 70));
        checkout.addItem(new Item("B", 10, 2, 15));
        checkout.addItem(new Item("C", 30, 4, 60));
        checkout.addItem(new Item("D", 25, 2, 40));
    }

    @Test
    public void testSingleItemWithoutSpecialPrice() {
        checkout.scan("B");
        assertEquals(10, checkout.calculateTotal());
    }

    @Test
    public void testMultipleItemsWithoutSpecialPrice() {
        checkout.scan("A");
        checkout.scan("B");
        assertEquals(50, checkout.calculateTotal());
    }

    @Test
    public void testItemWithSpecialPrice() {
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        assertEquals(70, checkout.calculateTotal());
    }

    @Test
    public void testItemWithSpecialPriceAndExtraUnits() {
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        assertEquals(110, checkout.calculateTotal());
    }

    @Test
    public void testDifferentItemsWithAndWithoutSpecialPrice() {
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("C");
        assertEquals(145, checkout.calculateTotal());
    }

    @Test
    public void testReceiptGeneration() {
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("C");
        checkout.scan("D");
        checkout.scan("D");
        String expectedReceipt = "Receipt:\n" +
            "A x 1 = 40 cents\n" +
            "B x 1 = 10 cents\n" +
            "C x 1 = 30 cents\n" +
            "D x 2 = 40 cents\n" +
            "Total: 120 cents\n";
        assertEquals(expectedReceipt, checkout.generateReceipt());
    }

    @Test
    public void testEmptyCart() {
        assertEquals(0, checkout.calculateTotal());
    }

    @Test
    public void testInvalidItemScan() {
        try {
            checkout.scan("X");
        } catch (IllegalArgumentException e) {
            assertEquals("Item not found: X", e.getMessage());
        }
    }
}
