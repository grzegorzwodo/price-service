package com.shoppingplatform.price.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Policy {
    private int quantity;
    private double discount;
}
