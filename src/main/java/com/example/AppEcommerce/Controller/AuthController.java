package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Dto.*;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Service.AuthService;
import com.example.AppEcommerce.Service.UserServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")// tnajm t7el il port wala ta5u il permission mn springsecurity config

public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserServicesI  userServicesI;


    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpAdmin signUpAdmin){
        return authService.registerAdmin(signUpAdmin);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpUser signUpUser){
        return authService.registerUser(signUpUser);
    }
   @PostMapping("/registerDelivery")
    public ResponseEntity<?> registerDelivery(@Valid @RequestBody SignUpDelivery signUpDelivery){
        return authService.registerDelivery(signUpDelivery);
    }
    @PostMapping("/SousAdmin")
    public ResponseEntity<?> SousAd(@Valid @RequestBody SignUpSousAdmin signUpDelivery){
        return authService.registerSousAdmin(signUpDelivery);
    }

    //liste livreur
    @GetMapping("/Livreurs")
    public List<User> livreur(){
        return userServicesI.Livreurs();
    }
    @GetMapping("/Admins")
    public List<User> Admin(){
        return userServicesI.AdminUsers();
    }


}
