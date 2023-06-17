package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Rating;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Model.UserPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPurchaseRepository extends MongoRepository<UserPurchase,String> {
    UserPurchase findUserById(String id);
}
