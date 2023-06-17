package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Model.Livreur;
import com.example.AppEcommerce.Repository.LivreurRepository;
import com.example.AppEcommerce.Service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping
@RestController
public class LivreurCont {


    @Autowired
    LivreurRepository livreurRepository;

    @GetMapping(path="/nbreLivreur")
    public int nbre(){
        List<Livreur> l=livreurRepository.findAll();
        return l.size();
    }
    @GetMapping(path="/ListLivreur")
    public List<Livreur> List(){
        return livreurRepository.findAll();
    }
    @GetMapping(path="/DetailLivreur/{id}")
    public Livreur detail(@PathVariable String id){
        return  livreurRepository.findById(id).orElseThrow();
    }

}
