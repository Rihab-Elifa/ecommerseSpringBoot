package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.PageVendor;


import com.example.AppEcommerce.Model.Produit;
import com.example.AppEcommerce.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends MongoRepository<PageVendor,String>
{
    PageVendor findByTitle(String name);



}

