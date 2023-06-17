package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Commander;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends MongoRepository<Commander,String> {

    List<Commander> findByidDelivery(String idDelivery);
    List<Commander> findByidSender(String idSender);
    List<Commander> findByidVendor(String idVendor);
}
