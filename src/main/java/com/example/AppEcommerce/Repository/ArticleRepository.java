package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Article;
import com.example.AppEcommerce.Model.PageVendor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {

    List<Article> findByPage(PageVendor page);

}