package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.ProduitDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProduitServiceImp {
    String addProduit(String idUser, ProduitDto produitDto, MultipartFile image) throws IOException;
    String updateProduit(String id,ProduitDto produitDto ,MultipartFile image) throws IOException;
    void delete(String idPage,String idProduit);

}
