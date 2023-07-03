package com.example.AppEcommerce.Model;


import com.example.AppEcommerce.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
     private String id;
    @Email
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private long phone;
    private String ville;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<PageVendor> pages =new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RevenueDate> revenueDates =new ArrayList<>();
    private int sold;

    private boolean enLigne;

    private double longitude;
 private boolean etat;
    private double latitude;
    private double compteurC;
    private double t;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "imageProfile_id")
    private File imageProfile;

    public User(String email, String password, Role role ) {
        this.email=email;
        this.password=password;
        this.role=role;
    }

    public User(String email, String password, Role role, String firstName, String lastName, long phone) {
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.role=role;
        this.phone=phone;

    }
    public User(String email, String password, String firstName, String lastName, long phone, Role role,  int sold, boolean enLigne,double longitude, double latitude){
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.role=role;
        this.sold = sold;
        this.enLigne = enLigne;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public User(String email, String password, String firstName, String lastName, long phone, Role role, List<PageVendor> pages, List<RevenueDate> revenueDates, int sold, boolean enLigne,boolean etat, double longitude,  double latitude) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.pages = pages;
        this.revenueDates = revenueDates;
        this.sold = sold;

        this.enLigne = enLigne;
        this.etat = etat;
        this.longitude = longitude;

        this.latitude = latitude;

    }

    public User(String email, String password, Role role, String firstName, String lastName, long phone,String ville) {
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.role=role;
        this.ville=ville;
        this.phone=phone;

    }

    public boolean isPresent() {
        return true;
    }


}
