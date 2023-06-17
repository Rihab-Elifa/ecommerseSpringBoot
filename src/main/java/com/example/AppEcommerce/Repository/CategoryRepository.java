package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Categorie;
import com.example.AppEcommerce.Model.PageVendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends MongoRepository<Categorie,String> {
    Categorie findByName(String name);
    void deleteByName(String n);

}
