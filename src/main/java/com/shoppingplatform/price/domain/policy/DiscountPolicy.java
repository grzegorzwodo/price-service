package com.shoppingplatform.price.domain.policy;

public interface DiscountPolicy {

    double applyDiscount(int quantity, double price);
}
