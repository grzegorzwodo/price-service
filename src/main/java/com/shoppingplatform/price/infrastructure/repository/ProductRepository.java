package com.shoppingplatform.price.infrastructure.repository;

import com.shoppingplatform.price.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final Map<UUID, Product> productStore = new HashMap<>();

    public void save(Product product) {
        productStore.put(product.getId(), product);
    }

    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(productStore.get(id));
    }
}
