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
public class SignUpSousAdmin {
    private String id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    private long phone;

    private String ville;
    private Role role;
}

