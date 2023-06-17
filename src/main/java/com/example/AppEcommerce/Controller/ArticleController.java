package com.example.AppEcommerce.Controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.AppEcommerce.Dto.*;
import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Model.*;

import com.example.AppEcommerce.Repository.ArticleRepository;
import com.example.AppEcommerce.Repository.CategoryRepository;
import com.example.AppEcommerce.Repository.UserPurchaseRepository;
import com.example.AppEcommerce.Service.ArticleService;
import com.example.AppEcommerce.Service.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserPurchaseRepository userPurchaseRepository;
    @PostMapping(value = "/addArticle/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addArticle(@PathVariable String id, @RequestPart("article") ArticleDto articleDto,@RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
        return articleService.addArticle(id, articleDto,image);
    }

    @PostMapping(value="/addProduit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addProduit(@PathVariable String id , @RequestPart("article") ArticleDto articleDto, @RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
        return articleService.addArticle(id, articleDto,image);


    }

    @PostMapping("/addImageToArticle/{id}")
    public ResponseEntity<?> addImageToArticle(@RequestPart(name = "image", required = false) MultipartFile file, @PathVariable String id) throws IOException {
        return articleService.addImageToArticle(id, file);
    }

    @GetMapping("/findArticlesByPage/{id}")
    public List<Article> findByPage(@PathVariable String id){
        return articleService.findByPage(id);
    }
    @PostMapping("/editimage/{id}")
    public ResponseEntity<?> editimage(@RequestPart(name = "image", required = false) MultipartFile file, @PathVariable String id) throws IOException {
        return articleService.editimage(id, file);
    }
    @PutMapping("/editArticle")
    public ResponseEntity<?> editArticle(@RequestBody ArticleDto ArticleDto) {
        return articleService.editArticle(ArticleDto);

    }
    @DeleteMapping(value = "/deleteArticle/{id}")
    public void deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
    }
    @GetMapping(value="/ListeArticle")
    public List<Article> liste(){return articleRepository.findAll();
    }
    @PostMapping("/articlesByCategory")
    public List<Article> articlesByCategory(@RequestBody Activity activity){
        return articleService.findByCategory(activity);
    }

    @PostMapping(value="/articlesLocal/{lat}/{lon}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> check(@RequestBody Activity activity,@PathVariable double lat, @PathVariable double lon){
        return articleService.findLocal(activity,lat,lon);
    }
    @GetMapping(value = "/getarticle/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleService.getArticleById(id);
    }
    //********************Rating user for article********

    @PostMapping(value="/addRating/{idU}/{idA}")
    public String Rating(@PathVariable String idU,@PathVariable String idA,@RequestBody RatingDto ratingDto){
        return ratingService.addRating(idU,idA,ratingDto);


    }
    @GetMapping(value="/somme/{idA}")
    public double somme(@PathVariable String idA){
        return articleService.moyenneRating(idA);
    }

    //recherche
    @GetMapping(value="/recherche/{i}")
    public List<Article> recherche(@PathVariable String i){
        return articleService.find(i);
    }



    //categorie
    @PostMapping(value = "/ajoutCat", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addArticle(@RequestPart("cat")CategDto categDto, @RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
        return articleService.addArticle(categDto,image);
    }

    @DeleteMapping(value = "/deleteCate/{id}")
    public void supprimerCat(@PathVariable String id) {
        categoryRepository.deleteById(id);
    }
    @GetMapping(value = "/AllCat")
    public void getAllCat(@PathVariable String id) {
        categoryRepository.findAll();
    }
    @PostMapping(value = "/addUserPurchase")
    public void addUserPurchase(@RequestBody UserPurchase userPurchase) {
              userPurchaseRepository.save(userPurchase);


    }

    public List<Products>getProduct(){
        List<Products> Products=new ArrayList<>();
        List<Article> a=articleRepository.findAll();

        for(Article aa:a){
            double price=Double.parseDouble(aa.getPrix());
            Products.add(new Products(aa.getId(),aa.getNom(),aa.getDescription(),price));
        }
        return Products;

    }

    //get List article recommend by user
    @GetMapping(value = "/ArticleRecommend/{id}")
  public   void getArticleRecommend(@PathVariable String id){
     RestTemplate restTemplate = new RestTemplate();
     UserPurchase u=userPurchaseRepository.findUserById(id);
     List<History> historique =u.getProductId();

     // Définissez les en-têtes de la requête
     HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
     // Créez le corps de la requête
     String requestBody = "{\" Products\": \"Products\",\"history\": \"historique\"}"; // Remplacez avec votre corps de requête réel
      List<Products> pp=getProduct();

       Recommend myData = new Recommend(pp, historique);

        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(myData);

            // Créez l'objet HttpEntity avec les en-têtes et le corps de la requête
            HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);

            // Envoyez la requête POST et obtenez la réponse
            ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:8000/recommend_products",
                    HttpMethod.POST, httpEntity, String.class);
            // Traitez la réponse

            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponceBody responseObj = objectMapper.readValue(responseBody, ResponceBody.class);
            for(Products r:responseObj.getRecommendedProducts() ){
                System.out.println(r.getName());
            }
            System.out.println(responseObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



 }



}
