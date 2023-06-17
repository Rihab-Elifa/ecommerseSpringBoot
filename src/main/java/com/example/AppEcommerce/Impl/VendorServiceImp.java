package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.VendorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VendorServiceImp {
    ResponseEntity<?> registerVendor(VendorDto vendorDto, MultipartFile file) throws IOException;
}
