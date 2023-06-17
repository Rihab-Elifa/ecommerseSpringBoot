package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Livreur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreurRepository extends MongoRepository<Livreur,String> {
}
