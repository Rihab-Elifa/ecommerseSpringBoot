package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating,String> {

}
