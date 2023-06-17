package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Model.Categorie;
import com.example.AppEcommerce.Repository.CategoryRepository;
import com.example.AppEcommerce.Repository.ProduitRepository;
import com.example.AppEcommerce.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class VendorCont {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProduitRepository produitRepository;


    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void setVendorRepository(VendorRepository v) {
        this.vendorRepository = v;
    }

    @GetMapping(value = "/findAll")
    public List<Categorie> findAll() {
        return categoryRepository.findAll();

    }
    @PostMapping(value="/add")
    public void addCat(@RequestBody Categorie c){
        categoryRepository.save(c);
    }
    @DeleteMapping(value="/delete")
    public void delete(@RequestBody String u){
        categoryRepository.deleteByName(u);
    }

    @PutMapping(value="/modifier/{id}")
   public void  modifier(@PathVariable ("id") String id,@RequestBody String name ){
       Optional<Categorie> cat =categoryRepository.findById(id);
       Categorie categorie=cat.get();
       categorie.setName(name);
        categoryRepository.save(categorie);

   }
    //Gestion du produit
   /* @PostMapping(value="/addProduit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addProduit(@PathVariable String id , @RequestPart("produit") ProduitDto produitDto, @RequestPart(name = "image", required = false) MultipartFile image) throws IOException {
        return produitServices.addProduit(id,produitDto,image);


    }
    /*@GetMapping(value="/findAllP/{id}")
    public List<Produit> findAll(@PathVariable String id) {
        Optional <PageVendor> v=vendorRepository.findById(id);
        PageVendor pp=v.get();
        return
    }

    @PutMapping(value="/update/{id}")
    public String update(@PathVariable String id,@RequestBody ProduitDto produitDto ,@RequestPart(name = "image", required = false) MultipartFile image )throws IOException {

        return produitServices.updateProduit(id,produitDto,image);
        //on fixe
    }
    //tous les produits
    @GetMapping(value="/ListProduit")
    public List<Produit> findA(){
        return produitRepository.findAll();
    }
    //delete produit
    @DeleteMapping(value="/supprimerProduit/{id}/{id2}")
    public void supprimer(@PathVariable String id  , String id2){ produitServices.delete(id,id2);}


    //liste des produit par categorie
    @GetMapping(value="/produitParCat/{name}")
    public List<Produit> listPByCat(@PathVariable String name) {
        List<Produit> pp = new ArrayList<>();
        for (Produit p : produitRepository.findAll()) {
            if (p.getC().getName() == name) {
                pp.add(p);

            }
        }
        return pp;
    }*/

}

