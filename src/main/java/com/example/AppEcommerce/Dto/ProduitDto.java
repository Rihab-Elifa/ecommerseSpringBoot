package com.example.AppEcommerce.Dto;

import com.example.AppEcommerce.Model.Categorie;
import com.example.AppEcommerce.Model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDto {
    private  String id;
    private String name;
    private String description;
    private double prix;

    private int Stock;
    private File image;
    private Categorie c;

}
