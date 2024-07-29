package com.shoppingplatform.price.service;

import com.shoppingplatform.price.application.ProductDiscountService;
import com.shoppingplatform.price.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static com.shoppingplatform.price.domain.model.DiscountType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductServiceTest {

    @Autowired
    private ProductDiscountService productDiscountService;

    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
        productDiscountService.addProduct(new Product(productId, BigDecimal.valueOf(10.0).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscount() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 10, AMOUNT);
        assertEquals(BigDecimal.valueOf(98.00).setScale(2, RoundingMode.HALF_UP), price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscount() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 10, PERCENTAGE);
        assertEquals(BigDecimal.valueOf(97.00).setScale(2, RoundingMode.HALF_UP), price);
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountNoDiscount() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 5, AMOUNT);
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_UP), price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountNoDiscount() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 5, PERCENTAGE);
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_UP), price);
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountHighQuantity() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 100, AMOUNT);
        assertEquals(BigDecimal.valueOf(995.00).setScale(2, RoundingMode.HALF_UP), price);
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountHighQuantity() {
        BigDecimal price = productDiscountService.calculatePrice(productId, 50, PERCENTAGE);
        assertEquals(BigDecimal.valueOf(475.00).setScale(2, RoundingMode.HALF_UP), price);
    }
}
