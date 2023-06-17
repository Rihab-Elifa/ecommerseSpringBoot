package com.example.AppEcommerce.Model;

import com.example.AppEcommerce.Dto.ArticleCaisse;
import com.example.AppEcommerce.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Commander {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String idSender;
    private String address;
    private String city;
    private String streetAddress;
    private String phone;
    private String selectedTime;

    private String description;
    private String idVendor;
    private double subtotal;
    private String frais;

    private double totalPrice;

    private List<ArticleCaisse> articles;
    String idDelivery;
    LocalDate date;
    private String reference;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Commander(String idSender,String address,String streetAddress,String phone,String selectedTime,String description,String idVendor,double subtotal,String frais,Double totalPrice, List<ArticleCaisse> articles, LocalDate date  ){
        this.idSender=idSender;
        this.address=address;
        this.streetAddress=streetAddress;
        this.phone=phone;
        this.selectedTime=selectedTime;
        this.description=description;
        this.idVendor=idVendor;
        this.subtotal=subtotal;
        this.frais=frais;
        this.totalPrice=totalPrice;
        this.articles = articles;
        this.date = date;
        this.reference = generateReference();
    }
    private String generateReference() {
        // Generate a unique reference with a prefix letter and numeric part
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Generate the prefix letter
        int randomIndex = random.nextInt(letters.length());
        sb.append(letters.charAt(randomIndex));

        // Generate the numeric part
        int numericPart = random.nextInt(9000) + 1000; // Generate a random 4-digit number
        sb.append("-").append(numericPart);

        return sb.toString();
    }
}
