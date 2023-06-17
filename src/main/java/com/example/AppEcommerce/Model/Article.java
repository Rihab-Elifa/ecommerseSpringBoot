package com.example.AppEcommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Article {
    @Id

    private String id;
    private String nom;
    private String description;
    private String prix;
    private String  nbstock;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private File image;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "page_id")
    private PageVendor page;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Rating> rating =new ArrayList<>();


    public Article(String nom, String description, String prix, String nbstock,PageVendor page,File image) {
        this.nom=nom;
        this.description=description;
        this.prix=prix;
        this.nbstock=nbstock;
        this.page=page;
        this.image=image;
    }


}
