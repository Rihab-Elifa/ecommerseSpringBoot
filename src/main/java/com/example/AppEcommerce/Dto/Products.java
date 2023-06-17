package com.example.AppEcommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private  String id;
    private String name;
    private String description;
    private double price;

    public Products( String name, String description, double price) {

        this.name = name;
        this.description = description;
        this.price = price;
    }
}
