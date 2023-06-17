package com.example.AppEcommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;
    private Double prix;
    private int stock;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private File image;
    @ManyToOne
    @JoinColumn(name="c_id")
    private Categorie c;



    public Produit( String name, String description, Double prix,int stock, File image, Categorie c){
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.stock=stock;
        this.image = image;
        this.c = c;
    }

}
