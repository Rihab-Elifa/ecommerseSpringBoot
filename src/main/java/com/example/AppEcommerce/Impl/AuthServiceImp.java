package com.example.AppEcommerce.Impl;


import com.example.AppEcommerce.Dto.LoginRequest;
import com.example.AppEcommerce.Dto.SignUpAdmin;
import com.example.AppEcommerce.Dto.SignUpDelivery;
import com.example.AppEcommerce.Dto.SignUpUser;
import org.springframework.http.ResponseEntity;

public interface AuthServiceImp {
    ResponseEntity<?> registerAdmin(SignUpAdmin signUpAdmin);

    ResponseEntity<?> registerUser(SignUpUser signUpUser);

    ResponseEntity<?> registerDelivery(SignUpDelivery signUpDelivery);

    ResponseEntity<?> login(LoginRequest loginRequest);
}
