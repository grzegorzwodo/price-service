package com.shoppingplatform.price.domain.policy;

import com.shoppingplatform.price.infrastructure.config.DiscountConfig;
import com.shoppingplatform.price.infrastructure.config.Policy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class PercentageBasedDiscountPolicy implements DiscountPolicy {

    private final Map<Integer, Double> discountMap;
    public PercentageBasedDiscountPolicy(DiscountConfig discountConfig) {
        Map<Integer, Double> discountMap = new HashMap<>();
        for (Policy policy : discountConfig.getPercentageBased()) {
            discountMap.put(policy.getQuantity(), policy.getDiscount());
        }
        this.discountMap = discountMap;
    }

    @Override
    public BigDecimal applyDiscount(int quantity, BigDecimal price) {
        // find max discount
        double maxDiscount = discountMap.entrySet()
            .stream()
            .filter(entry -> quantity >= entry.getKey())
            .map(Map.Entry::getValue)
            .max(Double::compareTo)
            .orElse(0.0);

        // convert to BigDecimal
        BigDecimal discount = BigDecimal.valueOf(maxDiscount).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount);
        return price.multiply(discountMultiplier);
    }
}
