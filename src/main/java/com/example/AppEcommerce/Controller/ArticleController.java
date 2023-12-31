package com.example.AppEcommerce.Controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.AppEcommerce.Dto.*;
import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Enum.Status;
import com.example.AppEcommerce.Model.*;

import com.example.AppEcommerce.Repository.*;
import com.example.AppEcommerce.Service.ArticleService;
import com.example.AppEcommerce.Service.CaisseService;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CaisseService caisseService;
    @Autowired
    private RatingRepository ratingRepository;
    @PostMapping(value = "/addArticle/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addArticle(@PathVariable String id, @RequestPart("article") ArticleDto articleDto,@RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
        return articleService.addArticle(id, articleDto,image);
    }

    @PostMapping(value="/addProduit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addProduit(@PathVariable String id , @RequestPart("article") ArticleDto articleDto, @RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
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
    public ResponseEntity<?> Rating(@PathVariable String idU,@PathVariable String idA,@RequestBody int ratingDto){
        return ratingService.addRating(idU,idA,ratingDto);


    }

    @DeleteMapping(value = "/delRating/{id}")
    public void delRat(@PathVariable String id) {
        ratingService.deleteRat(id);
    }
    /*************************************** get moyenn rating for article*/
    @GetMapping(value="/moyRating/{idU}")
    public ResponseEntity<Double> moyRating(@PathVariable String idU){
        return articleService.calculeMoyRating(idU);


    }
    //etatRating
    @GetMapping(value="/EtatRating/{idU}/{idA}")
    public boolean etatttt(@PathVariable String idU,@PathVariable String idA){
        return ratingService.ExistRat2(idU,idA);
    }
    ///getrating
    @GetMapping("/getReat/{idU}/{idA}")
    public int getReatby(@PathVariable String idU,@PathVariable String idA){
        return ratingService.getR(idU,idA);
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
    public List<ratings> getRating(){
        List<ratings> ratings =new ArrayList<>();
        List<Rating> a=ratingRepository.findAll();
        for(Rating aa:a){
            ratings.add(new ratings(aa.getNumR(),aa.getU(),aa.getA()));
        }
        System.out.println(ratings);
        return ratings;

    }


    //get List article recommend by user
    @GetMapping(value = "/ArticleRecommend/{id}")//on change id de userpurchase par id du user directement pour chercher le historique
  public   List<Article> getArticleRecommend(@PathVariable String id){
     RestTemplate restTemplate = new RestTemplate();
     //UserPurchase u=userPurchaseRepository.findUserById(id);
     //List<History> historique =u.getProductId();
     List<Commander> lc=caisseService.caisseListClient(id);
     List<History> historique=new ArrayList<>();
     for(Commander c :lc){

         c.getArticles().forEach(aa->{
             History h =new History(aa.getId());
          if(!(historique.contains(h)))
             historique.add(h);
         });
     }

     // Définissez les en-têtes de la requête
     HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
     // Créez le corps de la requête
     String requestBody = "{\" Products\": \"Products\",\"history\": \"historique\"}"; // Remplacez avec votre corps de requête réel
      List<Products> pp=getProduct();

       Recommend myData = new Recommend(pp, historique);

        ObjectMapper mapper = new ObjectMapper();
        List<Article> l=new ArrayList<>();

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

            List<Article> l2=articleRepository.findAll();
            for(Products r:responseObj.getRecommendedProducts() ){
                System.out.println(r.getName());
                for(Article ar:l2){
                    if (ar.getDescription().equals((r.getDescription()))&&ar.getNom().equals((r.getName()))){
                        l.add(ar);
                        System.out.println(ar.getNom());
                    }
                }
            }
            System.out.println(responseObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    return l;
 }
// Model-based collaborative filtering system

    //get List article recommend by user
    @GetMapping(value = "/ArticleR/{id}")
    public   List<Article> ArticleRec(@PathVariable String id){
        RestTemplate restTemplate = new RestTemplate();
        //UserPurchase u=userPurchaseRepository.findUserById(id);
        //List<History> historique =u.getProductId();
        List<Commander> lc=caisseService.caisseListClient(id);
        List<History> hp=new ArrayList<>();
        for(Commander c :lc){
            if(c.getStatus()== Status.DELIVERED){
                c.getArticles().forEach(aa->{
                    History h =new History(aa.getId());
                    if(!(hp.contains(h)))
                        hp.add(h);
                });
            }}

        // Définissez les en-têtes de la requête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // Créez le corps de la requête


        Recommend2 myData = new Recommend2(getRating(), hp);

        ObjectMapper mapper = new ObjectMapper();
        List<Article> l=new ArrayList<>();

        try {
            String json = mapper.writeValueAsString(myData);

            // Créez l'objet HttpEntity avec les en-têtes et le corps de la requête
            HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);

            // Envoyez la requête POST et obtenez la réponse
            ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:8000/recommend",
                    HttpMethod.POST, httpEntity, String.class);
            // Traitez la réponse

            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponceBody2 responseObj = objectMapper.readValue(responseBody, ResponceBody2.class);

            List<Article> l2=articleRepository.findAll();
            for(String r:responseObj.getRecommendedProducts() ){
                System.out.println(r);
                for(Article ar:l2){
                    if (ar.getId().equals(r)){
                        l.add(ar);
                        System.out.println(ar.getNom());
                    }
                }

            }
            System.out.println(responseObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return l;
    }


}
