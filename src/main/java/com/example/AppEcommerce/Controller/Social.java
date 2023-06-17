package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Dto.GoogleAuthRequest;
import com.example.AppEcommerce.Dto.JwtResonse;
import com.example.AppEcommerce.Dto.LoginRequest;
import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Repository.UserRepository;
import com.example.AppEcommerce.Security.jwt.JwtUtils;
import com.example.AppEcommerce.Service.AuthService;
import com.example.AppEcommerce.Service.UserServicesI;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.Payload;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/social")
@CrossOrigin("http://localhost:4200")
public class Social {
    private UserRepository userServicesI;
    private AuthService AuthService;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private String email;

    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    public Social(UserRepository userServicesI,  AuthService AuthService,JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userServicesI = userServicesI;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.AuthService= AuthService;



    }
    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody MessageResponse tokenDto) throws Exception {
        System.out.println("pass " + password);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport,factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDto.getMessage());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        email = payload.getEmail();
        User user = new User();
        if(userServicesI.existsByEmail(email)){
            user = userServicesI.findUserByEmail(email);
        } else {
            user = createUser(email);
        }
        ///////////////////////////
        LoginRequest jwtLogin = new LoginRequest();
        jwtLogin.setEmail(user.getEmail());
        jwtLogin.setPassword(password);
        ///////////////////////////

        return new ResponseEntity<>(AuthService.login(jwtLogin), HttpStatus.OK);
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userServicesI.save(user);
    }
    @PostMapping("/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody MessageResponse tokenDto) throws Exception {
        Facebook facebook = new FacebookTemplate(tokenDto.getMessage());
        String [] data = {"email"};
        org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class,data);

        email = user.getEmail();
        User userFace = new User();
        if(userServicesI.existsByEmail(email)){
            userFace = userServicesI.findUserByEmail(email);
        } else {
            userFace = createUser(email);
        }
        ///////////////////////////
        LoginRequest jwtLogin = new LoginRequest();
        jwtLogin.setEmail(user.getEmail());
        jwtLogin.setPassword(password);
        ///////////////////////////

        return new ResponseEntity<>(AuthService.login(jwtLogin), HttpStatus.OK);
    }

}
