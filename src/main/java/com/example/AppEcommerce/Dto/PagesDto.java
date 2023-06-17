package com.example.AppEcommerce.Dto;

import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Enum.Region;
import com.example.AppEcommerce.Model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagesDto {
    @Id
    private String id;
    private String title;
    private String address;
    private String email;
    private long phone;
    private long  postalCode;
    private Activity activity;
    //private List<Produit> p;
    private File imageProfile;
    private File imageCouverture;


    private Region region;
    private double longitude;

    private double latitude;
}
