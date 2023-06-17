package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.RatingDto;

public interface RatingServiceImp {
    String addRating(String idU, String idA, RatingDto ratingDto);
}
