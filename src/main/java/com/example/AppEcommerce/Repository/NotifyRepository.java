package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Notif;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepository extends MongoRepository<Notif,String> {
}
