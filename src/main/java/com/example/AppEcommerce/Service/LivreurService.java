package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Impl.LivreurRepositoryImp;
import com.example.AppEcommerce.Model.Livreur;
import com.example.AppEcommerce.Repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LivreurService implements LivreurRepositoryImp {

    @Autowired
    LivreurRepository livreurRepository;
    @Override
    public int nbreLivreur() {
        List<Livreur> l= livreurRepository.findAll();
        return l.size();
    }
}
