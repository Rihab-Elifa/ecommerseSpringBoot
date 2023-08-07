package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.PagesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PagesServicesImpl {
    ResponseEntity<String>  addPage(String idUser, PagesDto pagesDto, MultipartFile fileProfile, MultipartFile fileCouverture) throws IOException;


    ResponseEntity<String> editPage(String id, PagesDto pagesDto );

    ResponseEntity<?> editPhotoProfile(String id, MultipartFile fileProfile)throws IOException;

    ResponseEntity<?> editPhotoCouverture(String id, MultipartFile fileCouverture)throws IOException;

    void deletePage(String idPage, String idUser);
}
