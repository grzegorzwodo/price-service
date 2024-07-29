package com.shoppingplatform.price.application;

import com.shoppingplatform.price.domain.exception.DiscountTypeIsNotSupportedException;
import com.shoppingplatform.price.domain.exception.ProductNotFoundException;
import com.shoppingplatform.price.domain.model.DiscountType;
import com.shoppingplatform.price.domain.model.Product;
import com.shoppingplatform.price.domain.policy.AmountBasedDiscountPolicy;
import com.shoppingplatform.price.domain.policy.DiscountPolicy;
import com.shoppingplatform.price.domain.policy.PercentageBasedDiscountPolicy;
import com.shoppingplatform.price.infrastructure.config.DiscountConfig;
import com.shoppingplatform.price.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductDiscountService {
    private final ProductRepository productRepository;
    private final DiscountConfig discountConfig;

    private final Map<DiscountType, DiscountPolicy> allDiscountPolicy;

    public ProductDiscountService(ProductRepository productRepository, DiscountConfig discountConfig) {
        this.productRepository = productRepository;
        this.discountConfig = discountConfig;
        this.allDiscountPolicy = new HashMap<>();
        this.allDiscountPolicy.put(DiscountType.AMOUNT, new AmountBasedDiscountPolicy(discountConfig));
        this.allDiscountPolicy.put(DiscountType.PERCENTAGE, new PercentageBasedDiscountPolicy(discountConfig));
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public BigDecimal calculatePrice(UUID productId, int quantity, DiscountType discountType) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        BigDecimal originalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        DiscountPolicy discountPolicy = allDiscountPolicy.get(discountType);
        if(discountPolicy == null) {
            throw new DiscountTypeIsNotSupportedException("Invalid discount type");
        }

        return discountPolicy.applyDiscount(quantity, originalPrice).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrice(UUID productId, int quantity, DiscountPolicy... discountPolicies) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        for (DiscountPolicy policy : discountPolicies) {
            price = policy.applyDiscount(quantity, price);
        }
        return price;
    }

    public BigDecimal calculatePriceWithAllDiscounts(UUID id, int quantity) {
        return calculatePrice(id, quantity, new PercentageBasedDiscountPolicy(discountConfig), new AmountBasedDiscountPolicy(discountConfig));
    }
}
