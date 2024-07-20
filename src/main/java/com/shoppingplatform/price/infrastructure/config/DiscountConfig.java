package com.shoppingplatform.price.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "discount")
public class DiscountConfig {
    private List<Policy> amountBased;
    private List<Policy> percentageBased;
}
