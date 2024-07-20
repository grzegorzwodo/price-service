package com.shoppingplatform.price.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingplatform.price.domain.model.DiscountType;
import com.shoppingplatform.price.domain.model.Product;
import com.shoppingplatform.price.application.ProductDiscountService;
import com.shoppingplatform.price.api.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductDiscountService productDiscountService;

    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
        productDiscountService.addProduct(new Product(productId, 10.0));
    }

    @Test
    public void testAddProduct() throws Exception {
        ProductDto newProduct = new ProductDto();
        newProduct.setId(UUID.randomUUID());
        newProduct.setPrice(20.0);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
            .andExpect(status().isOk());
    }

    @Test
    public void testAddProductValidation() throws Exception {
        ProductDto newProduct = new ProductDto();
        newProduct.setId(null);
        newProduct.setPrice(-1.0);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.id", is("Product ID cannot be null")))
            .andExpect(jsonPath("$.price", is("Price must be greater than or equal to 0")));
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscount() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "10")
                .param("discountType", DiscountType.AMOUNT.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(98.0)));
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscount() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "10")
                .param("discountType", DiscountType.PERCENTAGE.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(97.0)));
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountNoDiscount() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "5")
                .param("discountType", DiscountType.AMOUNT.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(50.0)));
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountNoDiscount() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "5")
                .param("discountType", DiscountType.PERCENTAGE.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(50.0)));
    }

    @Test
    public void testCalculatePriceWithAmountBasedDiscountHighQuantity() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "100")
                .param("discountType", DiscountType.AMOUNT.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(995.0)));
    }

    @Test
    public void testCalculatePriceWithPercentageBasedDiscountHighQuantity() throws Exception {
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "50")
                .param("discountType", DiscountType.PERCENTAGE.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(475.0)));
    }

    @Test
    public void testProductNotFoundReturns404() throws Exception {
        UUID nonExistentProductId = UUID.randomUUID();
        mockMvc.perform(get("/api/products/{id}/price", nonExistentProductId)
                .param("quantity", "10")
                .param("discountType", DiscountType.AMOUNT.name()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$", is("Product not found with ID: " + nonExistentProductId)));
    }

    @Test
    public void testDiscountNotSupportedReturns400() throws Exception {
        String unsupportedDiscountType = "NewDiscountType";
        mockMvc.perform(get("/api/products/{id}/price", productId)
                .param("quantity", "50")
                .param("discountType", unsupportedDiscountType))
            .andExpect(status().isBadRequest());
    }
}
