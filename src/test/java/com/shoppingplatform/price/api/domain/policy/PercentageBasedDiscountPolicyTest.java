package com.shoppingplatform.price.api.domain.policy;

import com.shoppingplatform.price.domain.policy.PercentageBasedDiscountPolicy;
import com.shoppingplatform.price.infrastructure.config.DiscountConfig;
import com.shoppingplatform.price.infrastructure.config.Policy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercentageBasedDiscountPolicyTest {

    private PercentageBasedDiscountPolicy discountPolicy;

    @BeforeEach
    public void setUp() {
        // Create a list of policies for testing
        List<Policy> policies = new ArrayList<>();
        policies.add(new Policy(1, 5.0)); // 5% discount for 1 item
        policies.add(new Policy(5, 10.0)); // 10% discount for 5 items
        policies.add(new Policy(10, 15.0)); // 15% discount for 10 items

        // Create DiscountConfig with the list of policies
        DiscountConfig discountConfig = new DiscountConfig();
        discountConfig.setPercentageBased(policies);

        // Initialize the policy
        discountPolicy = new PercentageBasedDiscountPolicy(discountConfig);
    }

    @Test
    public void testApplyDiscount_NoDiscount() {
        // No discount should be applied for 0 items
        BigDecimal price = BigDecimal.valueOf(100.0);
        BigDecimal discountedPrice = discountPolicy.applyDiscount(0, price);
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), discountedPrice);
    }

    @Test
    public void testApplyDiscount_SingleItem() {
        // 5% discount should be applied for 1 item
        BigDecimal price = BigDecimal.valueOf(100.0);
        BigDecimal discountedPrice = discountPolicy.applyDiscount(1, price);
        assertEquals(BigDecimal.valueOf(95.00).setScale(2, RoundingMode.HALF_UP), discountedPrice);
    }

    @Test
    public void testApplyDiscount_MultipleItems() {
        // 10% discount should be applied for 5 items
        BigDecimal price = BigDecimal.valueOf(100.0);
        BigDecimal discountedPrice = discountPolicy.applyDiscount(5, price);
        assertEquals(BigDecimal.valueOf(90.00).setScale(2, RoundingMode.HALF_UP), discountedPrice);
    }

    @Test
    public void testApplyDiscount_MaximumDiscount() {
        // 15% discount should be applied for 10 items
        BigDecimal price = BigDecimal.valueOf(100.0);
        BigDecimal discountedPrice = discountPolicy.applyDiscount(10, price);
        assertEquals(BigDecimal.valueOf(85.00).setScale(2, RoundingMode.HALF_UP), discountedPrice);
    }

    @Test
    public void testApplyDiscount_AboveMaximum() {
        // 15% discount should be applied for quantities above 10
        BigDecimal price = BigDecimal.valueOf(100.0);
        BigDecimal discountedPrice = discountPolicy.applyDiscount(20, price);
        assertEquals(BigDecimal.valueOf(85.00).setScale(2, RoundingMode.HALF_UP), discountedPrice);
    }
}
