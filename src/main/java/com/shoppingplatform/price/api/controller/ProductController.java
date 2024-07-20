package com.shoppingplatform.price.api.controller;

import com.shoppingplatform.price.domain.model.DiscountType;
import com.shoppingplatform.price.domain.model.Product;
import com.shoppingplatform.price.application.ProductDiscountService;
import com.shoppingplatform.price.api.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductDiscountService productDiscountService;

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDTO) {
        Product product = new Product(productDTO.getId(), productDTO.getPrice());
        productDiscountService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping("/{id}/price")
    public double calculatePrice(@PathVariable UUID id, @RequestParam int quantity, @RequestParam DiscountType discountType) {
        return productDiscountService.calculatePrice(id, quantity, discountType);
    }

    @GetMapping("/{id}/priceWithAllDiscounts")
    public double calculatePriceWithAllDiscount(@PathVariable UUID id, @RequestParam int quantity) {
        return productDiscountService.calculatePriceWithAllDiscounts(id, quantity);
    }
}
