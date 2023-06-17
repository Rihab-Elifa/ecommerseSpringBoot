package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Dto.SignUpDelivery;
import com.example.AppEcommerce.Dto.SignUpUser;
import com.example.AppEcommerce.Dto.editLongLatDelivery;
import com.example.AppEcommerce.Enum.Role;
import com.example.AppEcommerce.Impl.UserServiceImpl;
import com.example.AppEcommerce.Model.File;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.RevenueDate;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Repository.FileRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServicesI implements UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
     @Autowired
     FileRepository fileRepository;



    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
    }


    @Override
    public String editUser(SignUpUser sign) {
        User user = getUserById(sign.getId());
        user.setEmail(sign.getEmail());
        if (!sign.getPassword().equals("laisser vide pour garder le dernier mot de passe")) {
            user.setPassword(encoder.encode(sign.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }

        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setPhone(sign.getPhone());
        User user1 = userRepository.save(user);
        return user1.getId();

    }

    @Override
    public List<PageVendor> listPagesByUser(String id) {
          Optional<User> vendor = userRepository.findById(id);
        List<PageVendor> l2=vendor.get().getPages();
        return l2;


    }

    @Override
    public User getUserByPage(String id) {
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
    }
    @Override
    public long getPhoneByUser(String id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("user not found with ID"+id));
        return user.getPhone();

    }

    @Override
    public void editPhone(long phone, String id){
        User user  = userRepository.findById(id) .orElseThrow(()-> new NoSuchElementException("user not found with ID"+id));
        user.setPhone(phone);
        userRepository.save(user);

    }


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
    @Override
    public void modifyStatusDelivery(String id){
        User user  = userRepository.findById(id) .orElseThrow(()-> new NoSuchElementException("user not found with ID"+id));
        user.setEnLigne(!user.isEnLigne());
        userRepository.save(user);
    }

    @Override
    public String editDelivery(SignUpDelivery signUpDelivery){
        User user = getUserById(signUpDelivery.getId());
        user.setEmail(signUpDelivery.getEmail());
        if(!signUpDelivery.getPassword().equals("laisser vide pour garder le dernier mot de passe")){
            user.setPassword(encoder.encode(signUpDelivery.getPassword()));
        }
        else{
            user.setPassword(user.getPassword());
        }
        user.setFirstName(signUpDelivery.getFirstName());
        user.setLastName(signUpDelivery.getLastName());
        user.setPhone(signUpDelivery.getPhone());
        User user1 =userRepository.save(user);
        return user1.getId();
    }
    @Override
    public String editLongLatDelivery(editLongLatDelivery longLatDelivery){
        User user = getUserById(longLatDelivery.getIdDelivery());
        user.setLatitude(longLatDelivery.getLatitude());
        user.setLongitude(longLatDelivery.getLongitude());
        User userUpdated = userRepository.save(user);
        return userUpdated.getId();
    }
    @Override
    public ResponseEntity<?> addImagesToUser(String id, MultipartFile fileProfile)throws IOException {
        File imageProfile = new File(fileProfile.getOriginalFilename(), fileProfile.getContentType(), fileProfile.getBytes());
        Optional<User> user= userRepository.findById(id);
        fileRepository.save(imageProfile);
        user.get().setImageProfile(imageProfile);
        userRepository.save(user.get());
        return ResponseEntity.ok(new MessageResponse("images registred succssefuly to user "));
    }
    @Override
    public RevenueDate todayRevenue(String id) {
        User user = getUserById(id);
        RevenueDate todayRevenue = null;
        List<RevenueDate> revenues = user.getRevenueDates();
        for (RevenueDate revenueDate : revenues) {
            if (revenueDate.getDate().equals(LocalDate.now())) {
                todayRevenue = revenueDate;
            }
        }
        return todayRevenue;
    }
    @Override
    public List<RevenueDate> weekRevenue(String id) {
        User user = getUserById(id);
        List<RevenueDate> weekRevenue = new ArrayList<>();
        List<RevenueDate> revenues = user.getRevenueDates();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = currentDate.minusDays(i);

            for (RevenueDate revenueDate : revenues) {
                if (revenueDate.getDate().equals(targetDate)) {
                    weekRevenue.add(revenueDate);
                    break; // Found the revenue for the target date, no need to continue searching
                }
            }
        }

        return weekRevenue;
    }
    @Override
    public List<RevenueDate> monthRevenue(String id) {
        User user = getUserById(id);
        List<RevenueDate> monthRevenue = new ArrayList<>();
        List<RevenueDate> revenues = user.getRevenueDates();
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();

        for (RevenueDate revenueDate : revenues) {
            if (revenueDate.getDate().getMonthValue() == currentMonth) {
                monthRevenue.add(revenueDate);
            }
        }

        return monthRevenue;
    }

    //List du livreur
    @Override
    public List<User> Livreurs() {
        List<User> u=userRepository.findAll();
        List<User> livreur=new ArrayList<>();
        for(User uu:u){
            if (uu.getRole()== Role.DELIVERY){
                livreur.add(uu);

            }
        }

        return livreur;

    }
    //get Admin

    @Override
    public List<User> AdminUsers() {
        List<User> u=userRepository.findAll();
        List<User> admin=new ArrayList<>();
        for(User uu:u){
            if (uu.getRole()== Role.ADMIN){
                admin.add(uu);

            }
        }

        return admin;

    }


}