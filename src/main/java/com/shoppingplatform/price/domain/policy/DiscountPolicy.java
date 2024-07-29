package com.shoppingplatform.price.domain.policy;

import java.math.BigDecimal;

public interface DiscountPolicy {

    BigDecimal applyDiscount(int quantity, BigDecimal price);
}
