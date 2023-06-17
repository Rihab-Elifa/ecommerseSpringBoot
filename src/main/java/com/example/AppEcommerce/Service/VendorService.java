package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Dto.VendorDto;
import com.example.AppEcommerce.Enum.Role;
import com.example.AppEcommerce.Impl.VendorServiceImp;

import com.example.AppEcommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.example.AppEcommerce.Model.File;

/*@Service
public class VendorService implements VendorServiceImp {
    @Autowired
    UserRepository  userRepository;
    @Autowired
    PasswordEncoder encoder;
    /*@Override
    public ResponseEntity<?> registerVendor(VendorDto vendorDto, MultipartFile file) throws IOException {
        File image = new File(file.getOriginalFilename(),file.getContentType(),file.getBytes());
        if (userRepository.existsByEmail(vendorDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:email is already taken!"));
        }
        vendor vendor  =new vendor(vendorDto.getEmail(),encoder.encode(vendorDto.getPassword()), Role.VENDOR,vendorDto.getActivity(),vendorDto.getAddress(),image);
        userRepository.save(vendor);
        return ResponseEntity.ok(new MessageResponse("vendor registered successfully!"));


    }




}*/
