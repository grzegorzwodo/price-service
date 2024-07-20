package com.shoppingplatform.price.domain.policy;

import com.shoppingplatform.price.infrastructure.config.DiscountConfig;
import com.shoppingplatform.price.infrastructure.config.Policy;

import java.util.HashMap;
import java.util.Map;

public class AmountBasedDiscountPolicy implements DiscountPolicy {

    private final Map<Integer, Double> discountMap;
    public AmountBasedDiscountPolicy(DiscountConfig discountConfig) {
        Map<Integer, Double> discountMap = new HashMap<>();
        for (Policy policy : discountConfig.getAmountBased()) {
            discountMap.put(policy.getQuantity(), policy.getDiscount());
        }
        this.discountMap = discountMap;
    }

    @Override
    public double applyDiscount(int quantity, double price) {
        return price - discountMap.entrySet()
            .stream()
            .filter(entry -> quantity >= entry.getKey())
            .map(Map.Entry::getValue)
            .max(Double::compareTo)
            .orElse(0.0);
    }
}
