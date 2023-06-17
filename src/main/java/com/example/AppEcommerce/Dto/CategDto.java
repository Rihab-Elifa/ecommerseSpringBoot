package com.example.AppEcommerce.Dto;


import com.example.AppEcommerce.Model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategDto {
    private String id;
    private String name;
    private File image;
}
