package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.ArticleDto;
import com.example.AppEcommerce.Dto.RatingDto;
import com.example.AppEcommerce.Impl.RatingServiceImp;
import com.example.AppEcommerce.Model.Article;
import com.example.AppEcommerce.Model.Rating;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Repository.ArticleRepository;
import com.example.AppEcommerce.Repository.PagesRepository;
import com.example.AppEcommerce.Repository.RatingRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements RatingServiceImp {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PagesRepository pagesRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Override
    public String addRating(String idU, String idA, RatingDto ratingDto) {
        User u =userRepository.findById(idU).orElseThrow();
        Article a =articleRepository.findById(idA).orElseThrow();
        Rating r=new Rating(ratingDto.getNumR(),u);
        ratingRepository.save(r);
        a.getRating().add(r);
        articleRepository.save(a);

        return r.getId();
    }
}
