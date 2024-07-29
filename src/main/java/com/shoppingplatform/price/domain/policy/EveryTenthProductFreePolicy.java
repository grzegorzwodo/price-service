package com.shoppingplatform.price.domain.policy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EveryTenthProductFreePolicy implements DiscountPolicy {

    @Override
    public BigDecimal applyDiscount(int quantity, BigDecimal price) {
        int freeItems = quantity / 10;

        // Obliczenie ceny jednostkowej jako BigDecimal
        BigDecimal unitPrice = price.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP);

        // Obliczenie kwoty zniżki
        BigDecimal discountAmount = unitPrice.multiply(BigDecimal.valueOf(freeItems));

        // Obliczenie ceny po zniżce
        return price.subtract(discountAmount);
    }
}
