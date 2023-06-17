package com.example.AppEcommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeDto {
    @Id

   String id;

     String idSender;
    String address;
    String city;
   String streetAddress;
    String phone;
    String selectedTime;

   String description;
     String idVendor;
   double subtotal;
    String frais;

    double totalPrice;

   List<ArticleCaisse> articles;
}
