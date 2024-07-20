package com.shoppingplatform.price.api.domain.policy;

import com.shoppingplatform.price.domain.policy.AmountBasedDiscountPolicy;
import com.shoppingplatform.price.infrastructure.config.DiscountConfig;
import com.shoppingplatform.price.infrastructure.config.Policy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountBasedDiscountPolicyTest {
    private AmountBasedDiscountPolicy discountPolicy;

    @BeforeEach
    public void setUp() {
        // Create a list of policies for testing
        List<Policy> policies = new ArrayList<>();
        policies.add(new Policy(1, 5.0)); // 5% discount for 1 item
        policies.add(new Policy(5, 10.0)); // 10% discount for 5 items
        policies.add(new Policy(10, 15.0)); // 15% discount for 10 items

        // Create DiscountConfig with the list of policies
        DiscountConfig discountConfig = new DiscountConfig();
        discountConfig.setAmountBased(policies);

        // Initialize the policy
        discountPolicy = new AmountBasedDiscountPolicy(discountConfig);
    }

    @Test
    public void testApplyDiscount_NoDiscount() {
        // No discount should be applied for 0 items
        double price = 100.0;
        double discountedPrice = discountPolicy.applyDiscount(0, price);
        assertEquals(100.0, discountedPrice);
    }

    @Test
    public void testApplyDiscount_SingleItem() {
        // 5% discount should be applied for 1 item
        double price = 100.0;
        double discountedPrice = discountPolicy.applyDiscount(1, price);
        assertEquals(95.0, discountedPrice);
    }

    @Test
    public void testApplyDiscount_MultipleItems() {
        // 10% discount should be applied for 5 items
        double price = 100.0;
        double discountedPrice = discountPolicy.applyDiscount(5, price);
        assertEquals(90.0, discountedPrice);
    }

    @Test
    public void testApplyDiscount_MaximumDiscount() {
        // 15% discount should be applied for 10 items
        double price = 100.0;
        double discountedPrice = discountPolicy.applyDiscount(10, price);
        assertEquals(85.0, discountedPrice);
    }

    @Test
    public void testApplyDiscount_AboveMaximum() {
        // 15% discount should be applied for quantities above 10
        double price = 100.0;
        double discountedPrice = discountPolicy.applyDiscount(20, price);
        assertEquals(85.0, discountedPrice);
    }

}
