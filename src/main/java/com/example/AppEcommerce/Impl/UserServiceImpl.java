package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.SignUpDelivery;
import com.example.AppEcommerce.Dto.SignUpUser;
import com.example.AppEcommerce.Dto.editLongLatDelivery;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.RevenueDate;
import com.example.AppEcommerce.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserServiceImpl {
    User getUserById(String id);
    String editUser(SignUpUser SignUpUser);
    List<PageVendor> listPagesByUser(String id);

    long getPhoneByUser(String id);



    void editPhone(long phone, String id);

    User getUserByPage(String idP);

    /* @Override
         public User getUserByPage(String id){
             final User[] userr = {null};
             List<User> users = userRepository.findAll();
             users.forEach(user -> {
                 user.getPages().forEach(page -> {
                     if(page.getId().equals(id)){
                         userr[0] = user;
                     }
                 });
             });
             return userr[0];
         }*/
    void modifyStatusDelivery(String id);

    String editDelivery(SignUpDelivery signUpDelivery);

    String editLongLatDelivery(editLongLatDelivery longLatDelivery);



    ResponseEntity<?> addImagesToUser(String id, MultipartFile fileProfile)throws IOException;

    RevenueDate todayRevenue(String id);

    List<RevenueDate> weekRevenue(String id);

    List<RevenueDate> monthRevenue(String id);

    //List du livreur
    List<User> Livreurs();

    List<User> AdminUsers();
}
