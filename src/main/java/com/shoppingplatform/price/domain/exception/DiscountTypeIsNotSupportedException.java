package com.shoppingplatform.price.domain.exception;

public class DiscountTypeIsNotSupportedException extends RuntimeException {
    public DiscountTypeIsNotSupportedException(String message) {
        super(message);
    }
}
