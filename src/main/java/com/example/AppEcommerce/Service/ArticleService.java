package com.example.AppEcommerce.Service;


import com.example.AppEcommerce.Dto.ArticleDto;
import com.example.AppEcommerce.Dto.CategDto;
import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Impl.ArticleServiceImpl;
import com.example.AppEcommerce.Model.Article;
import com.example.AppEcommerce.Model.Categorie;
import com.example.AppEcommerce.Model.File;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Repository.ArticleRepository;
import com.example.AppEcommerce.Repository.CategoryRepository;
import com.example.AppEcommerce.Repository.FileRepository;
import com.example.AppEcommerce.Repository.PagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleService  implements ArticleServiceImpl {
    @Autowired
    private PagesRepository pagesRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FileRepository fileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    int sum=0;

    public static final double RADIUS_OF_EARTH = 6371;
    @Override
    public ResponseEntity<String>  addArticle(String id, ArticleDto articleDto,MultipartFile image) throws IOException {
        PageVendor page =pagesRepository.findById(id).orElseThrow(null);
        File images = new File(image.getOriginalFilename(), image.getContentType(), image.getBytes());
        fileRepository.save(images);
        Article article = new Article(articleDto.getNom(), articleDto.getDescription(), articleDto.getPrix(), articleDto.getNbstock(), page,images);
        Article article1 = articleRepository.save(article);

        return ResponseEntity.ok().body("{\"pageId\": \"" + article1.getId() + "\"}");
    }
    @Override
    public ResponseEntity<?> addImageToArticle(String id, MultipartFile file)throws IOException {
        File image = new File(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Optional<Article> article = articleRepository.findById(id);
        fileRepository.save(image);
        article.get().setImage(image);
        articleRepository.save(article.get());
        return ResponseEntity.ok(new MessageResponse("images registred succssefuly to article  "));
    }
    @Override
    public List<Article> findByPage(String id){
        PageVendor page = pagesRepository.findById(id).orElseThrow(null);
        return articleRepository.findByPage(page);
    }
    @Override
    public ResponseEntity<?> editimage(String id, MultipartFile file)throws IOException {
        File image = new File(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Article article = articleRepository.findById(id).orElseThrow(null);;
        fileRepository.save(image);
        article.setImage(image);
        articleRepository.save(article);
        return ResponseEntity.ok(new MessageResponse("image edit successufuly "));
    }
    @Override
    public ResponseEntity<?> editArticle(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).orElseThrow(null);
        article.setNom(articleDto.getNom());
        article.setDescription(articleDto.getDescription());
        article.setPrix(articleDto.getPrix());
        article.setNbstock(articleDto.getNbstock());
        articleRepository.save(article);
        return ResponseEntity.ok(new MessageResponse("Article edit successufuly "));
    }
    @Override
    public void deleteArticle(String id) {
        Article article = articleRepository.findById(id).orElseThrow(null);
        articleRepository.delete(article);
    }
      //calcul moyenne du rating
    @Override
    public double moyenneRating(String idA) {
       Article a= articleRepository.findById(idA).orElseThrow();

       a.getRating().forEach(rating -> {
         sum+= rating.getNumR();
                });
        return (sum/a.getRating().size());
    }

    @Override
    public List<Article> findByCategory(Activity activity){
        List<PageVendor> pages = pagesRepository.findByActivity(activity);
        List<Article> articles = articleRepository.findAll();
        List<Article> newArticles = new ArrayList<>();
        articles.forEach(article -> {
            pages.forEach(page-> {
                if(article.getPage().getActivity() != Activity.RESTAURANTS && article.getPage().getActivity() != Activity.SUPERETTE && article.getPage().getActivity() != Activity.PATISSERIE ){
                    if(article.getPage().getId().equals(page.getId())){
                        newArticles.add(article);
                    }
                }
            });
        });
        return newArticles;

    }
    public double calculate(double lat1, double long1,
                            double lat2, double long2){
        double deltaLat = Math.toRadians(lat2 - lat1 );
        double deltaLon = Math.toRadians(long2 - long1 );
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIUS_OF_EARTH * c;
    }


    public List<Article> articlesLess7(double lat1, double long1){
        List<Article> articles = articleRepository.findAll();
        List<Article> articles1 = new ArrayList<>();
        articles.forEach(article ->{
            if(calculate(lat1,long1, article.getPage().getLatitude(), article.getPage().getLongitude()) < 7){
                articles1.add(article);
            }
        } );
        return
                articles1;
    }

    @Override
    public List<Article> findLocal(Activity activity,double lat1, double long1){
        List<PageVendor> pages = pagesRepository.findByActivity(activity);
        List<Article> articles = articlesLess7(lat1,long1);
        List<Article> newArticles = new ArrayList<>();
        articles.forEach(article -> {
            pages.forEach(page-> {

                if(article.getPage().getId().equals(page.getId())){
                    newArticles.add(article);
                }


            });
        });
        return newArticles;

    }
    @Override
    public Article getArticleById(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Page not found with id " + id));
    }

    //recherche
    @Override
    public List<Article> find(String i) {
        List<Article> articles = new ArrayList<>();
        List<Article> arts = articleRepository.findAll();
        for (Article a : arts) {
            if (a.getNom().contains(i)) {
                articles.add(a);
            }


        }
        return arts;
    }

    @Override
    public String  addArticle(CategDto categDto, MultipartFile image) throws IOException {

        File images = new File(image.getOriginalFilename(), image.getContentType(), image.getBytes());
        fileRepository.save(images);
       Categorie cat = new Categorie(categDto.getName(),images);
        Categorie article1 = categoryRepository.save(cat);
        return article1.getId();
    }


}
