package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.RatingDto;
import com.example.AppEcommerce.Model.Rating;
import org.springframework.http.ResponseEntity;

public interface RatingServiceImp {
    ResponseEntity<?> addRating(String idU, String idA, int ratingDto);



    ResponseEntity<?>  modifier(String id, String idA, int ratingDto);

    void deleteRat(String id);


    Rating ExistRat(String idU, String idA);
    boolean ExistRat2(String idU, String idA);

    int getR(String idU, String idA);
}
