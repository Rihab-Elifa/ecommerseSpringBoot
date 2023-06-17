package com.example.AppEcommerce.Dto;

import com.example.AppEcommerce.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDelivery {
    private String id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    private long phone;
    private Role role;
    private int revenue;
    private int sold;
    private boolean enLigne;
    private double longitude;
    private double latitude;


}
