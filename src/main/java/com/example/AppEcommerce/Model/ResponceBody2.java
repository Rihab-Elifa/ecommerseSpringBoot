package com.example.AppEcommerce.Model;

import com.example.AppEcommerce.Dto.Products;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponceBody2 {
    @JsonProperty("recommended_products")
    private List<String> recommended_products;

    public List<String> getRecommendedProducts() {
        return recommended_products;
    }

    public void setRecommendedProducts(List<String> recommendedProducts) {
        this.recommended_products = recommendedProducts;
    }
}
