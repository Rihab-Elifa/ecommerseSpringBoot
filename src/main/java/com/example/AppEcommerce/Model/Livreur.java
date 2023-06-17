package com.example.AppEcommerce.Model;

import com.example.AppEcommerce.Dto.ArticleCaisse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nom;
    private String email;
    private double phone;
    private String local;

    private List<ArticleCaisse> list;
}
