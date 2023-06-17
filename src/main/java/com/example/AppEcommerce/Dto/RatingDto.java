package com.example.AppEcommerce.Dto;

import com.example.AppEcommerce.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    @Id
    private String id;
    private int NumR;
    private User u;
}
