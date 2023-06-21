package com.example.AppEcommerce.Impl;


import com.example.AppEcommerce.Dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthServiceImp {
    ResponseEntity<?> registerAdmin(SignUpAdmin signUpAdmin);

    ResponseEntity<?> registerUser(SignUpUser signUpUser);

    ResponseEntity<?> registerDelivery(SignUpDelivery signUpDelivery);
    ResponseEntity<?> registerSousAdmin(SignUpSousAdmin signUpAdmin);

    ResponseEntity<?> login(LoginRequest loginRequest);
}
