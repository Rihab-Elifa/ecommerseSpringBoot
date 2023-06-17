package com.example.AppEcommerce.Repository;


import com.example.AppEcommerce.Enum.Role;
import com.example.AppEcommerce.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends MongoRepository<User,String> {
  User findUserByEmail(String email);
  boolean existsByEmail(String email);
 List<User> findDeliveryByRole(Role role);
}
