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

    public double calculatePrice(UUID productId, int quantity, DiscountType discountType) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        double originalPrice = product.getPrice() * quantity;

        DiscountPolicy discountPolicy = allDiscountPolicy.get(discountType);
        if(discountPolicy == null) {
            throw new DiscountTypeIsNotSupportedException("Invalid discount type");
        }

        return discountPolicy.applyDiscount(quantity, originalPrice);
    }

    public double calculatePrice(UUID productId, int quantity, DiscountPolicy... discountPolicies) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        double price = product.getPrice() * quantity;
        for (DiscountPolicy policy : discountPolicies) {
            price = policy.applyDiscount(quantity, price);
        }
        return price;
    }

    public double calculatePriceWithAllDiscounts(UUID id, int quantity) {
        return calculatePrice(id, quantity, new PercentageBasedDiscountPolicy(discountConfig), new AmountBasedDiscountPolicy(discountConfig));
    }
}
