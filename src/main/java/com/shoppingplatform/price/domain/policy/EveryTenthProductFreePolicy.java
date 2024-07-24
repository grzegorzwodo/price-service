package com.shoppingplatform.price.domain.policy;

public class EveryTenthProductFreePolicy implements DiscountPolicy {

    @Override
    public double applyDiscount(int quantity, double price) {
        int freeItems = quantity / 10;
        double discountAmount = freeItems * (price / quantity);
        return price - discountAmount;
    }
}
