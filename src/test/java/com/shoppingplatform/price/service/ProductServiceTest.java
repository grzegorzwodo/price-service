package com.shoppingplatform.price.service;

import com.shoppingplatform.price.application.ProductDiscountService;
import com.shoppingplatform.price.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static com.shoppingplatform.price.domain.model.DiscountType.*;
import static com.shoppingplatform.price.domain.model.DiscountType.PERCENTAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductServiceTest {

    @Autowired
    private ProductDiscountService productDiscountService;

    private UUID productId;
    private Product product;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
        product = new Product(productId, 10.0);
        productDiscountService.addProduct(product);
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscount() {
        double price = productDiscountService.calculatePrice(productId, 10, AMOUNT);
        assertEquals(98.0, price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscount() {
        double price = productDiscountService.calculatePrice(productId, 10, PERCENTAGE);
        assertEquals(97.0, price);
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountNoDiscount() {
        double price = productDiscountService.calculatePrice(productId, 5, AMOUNT);
        assertEquals(50.0, price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountNoDiscount() {
        double price = productDiscountService.calculatePrice(productId, 5, PERCENTAGE);
        assertEquals(50.0, price);
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountHighQuantity() {
        double price = productDiscountService.calculatePrice(productId, 100, AMOUNT);
        assertEquals(995.0, price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountHighQuantity() {
        double price = productDiscountService.calculatePrice(productId, 50, PERCENTAGE);
        assertEquals(475.0, price);
    }
}
