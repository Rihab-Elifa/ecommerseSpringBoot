package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Impl.RatingServiceImp;
import com.example.AppEcommerce.Model.Article;
import com.example.AppEcommerce.Model.Rating;
import com.example.AppEcommerce.Repository.ArticleRepository;
import com.example.AppEcommerce.Repository.PagesRepository;
import com.example.AppEcommerce.Repository.RatingRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponseEntity<?>  addRating(String idU, String idA, int ratingDto) {

        Rating ExistRat=ExistRat(idU,idA);
        if(ExistRat!=null){
            modifier(ExistRat.getId(),idA,ratingDto);
        }
        else{     Article a =articleRepository.findById(idA).orElseThrow();
            Rating r=new Rating(ratingDto,idU,idA);
            ratingRepository.save(r);
            a.getRating().add(r);
            articleRepository.save(a);}


        return ResponseEntity.ok(new MessageResponse("rating ajouter successufuly "));
    }
    @Override
    public ResponseEntity<?>  modifier(String id, String idA, int ratingDto) {
        Rating r=ratingRepository.findById(id).orElseThrow();
        r.setNumR(ratingDto);
        Article a =articleRepository.findById(idA).orElseThrow();
        ratingRepository.save(r);
        for (Rating rati: a.getRating()) {
            if(r.getId().equals(rati.getId())){
                rati.setNumR(ratingDto);
                articleRepository.save(a);
            }

        }
        articleRepository.save(a);
        return  ResponseEntity.ok(new MessageResponse("rating edit successufuly "));


    }
    @Override
    public void deleteRat(String id) {
       Rating r= ratingRepository.findById(id).orElseThrow(null);
        ratingRepository.delete(r);


    }
    @Override
    public Rating ExistRat(String idU, String idA) {
        List<Rating> LR=ratingRepository.findAll();

        for(Rating ra:LR){
            if ((ra.getU().equals(idU))&&(ra.getA().equals(idA))){
               return ra;
            }

        }
        return null;
    }
    @Override
    public boolean ExistRat2(String idU, String idA) {
        List<Rating> LR=ratingRepository.findAll();

        for(Rating ra:LR){
            if ((ra.getU().equals(idU))&&(ra.getA().equals(idA))){
                return true;
            }

        }
        return false;
    }
    @Override
    public int getR(String idU, String idA) {

        Rating ExistRat=ExistRat(idU,idA);
        if(ExistRat!=null){
           return ExistRat.getNumR();
        }
        else{
            return 0;
        }
        }


}
