package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Device;
import com.example.AppEcommerce.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeviceRepository extends MongoRepository<Device,String> {
    Optional<Device> findOneByToken(String token);
    List<Device> findAllByUserIdIn(Set<String> usersIds);
    List<Device> findByUser(User user);
}
