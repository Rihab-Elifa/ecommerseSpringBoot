package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Model.PageVendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesRepository extends MongoRepository<PageVendor,String> {
    List<PageVendor> findByActivity(Activity activity);


}
