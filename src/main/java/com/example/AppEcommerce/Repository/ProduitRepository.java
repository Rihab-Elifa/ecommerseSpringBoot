package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends MongoRepository<Produit,String> {


}
