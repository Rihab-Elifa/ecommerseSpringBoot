package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Dto.SignUpDelivery;
import com.example.AppEcommerce.Dto.SignUpUser;
import com.example.AppEcommerce.Dto.editLongLatDelivery;
import com.example.AppEcommerce.Model.RevenueDate;
import com.example.AppEcommerce.Model.User;

import com.example.AppEcommerce.Repository.UserRepository;
import com.example.AppEcommerce.Service.CaisseService;
import com.example.AppEcommerce.Service.UserServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/User")
public class UserCont {
   @Autowired
     private  UserServicesI userServicesI;
   @Autowired
   private UserRepository userRepository;

   @Autowired
   private CaisseService caisseService;
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userServicesI.getUserById(id);

    }
    @GetMapping("/email/{email}")
    public User getUserByemail(@PathVariable("email") String email) {
        return userRepository.findUserByEmail(email);

    }

    @GetMapping("/listeUser")
    public List<User> getUser() {
        return userRepository.findAll();

    }

    @PutMapping("/edit")
    public String  edit(@RequestBody SignUpUser signUpUser) {
        return userServicesI.editUser(signUpUser);

    }
    @GetMapping("/Phone/{id}")
    public long  getPhone(@PathVariable("id") String id) {
        return userServicesI.getPhoneByUser(id);

    }
    @PostMapping("/editPhone/{id}")
    public void editPhone( @PathVariable("id") String id ,@RequestBody long  phone ) {
        userServicesI.editPhone(phone,id);
    }
    @GetMapping("/modifyStatusDelivery/{id}")
    public void modifyStatusDelivery(@PathVariable("id") String id) {
        userServicesI.modifyStatusDelivery(id);
    }
    @PutMapping("/editDelivery")
    public String  edit(@RequestBody SignUpDelivery signUpDelivery) {
        return userServicesI.editDelivery(signUpDelivery);
    }
    @PutMapping("/editDeliveryLatLong")
    public String editLongLatDelivery(@RequestBody editLongLatDelivery longLatDelivery) {
        return userServicesI.editLongLatDelivery(longLatDelivery);
    }
    @PostMapping("/addImagesToUser/{id}")
    public ResponseEntity<?> addImagesToPage(@RequestPart(name = "imageProfile", required = false) MultipartFile fileProfile, @RequestPart(name = "imageCouverture", required = false) MultipartFile fileCouverture, @PathVariable String id) throws IOException {
        return userServicesI.addImagesToUser(id, fileProfile);
    }
    @GetMapping("/todayRevenue/{id}")
    public RevenueDate todayRevenue(@PathVariable("id") String id) {
        return userServicesI.todayRevenue(id);
    }

    @GetMapping("/weekRevenue/{id}")
    public List<RevenueDate> weekRevenue(@PathVariable("id") String id) {
        return userServicesI.weekRevenue(id);
    }

    @GetMapping("/monthRevenue/{id}")
    public List<RevenueDate> monthRevenue(@PathVariable("id") String id) {
        return userServicesI.monthRevenue(id);
    }
    //work successfuly for admin
    @GetMapping("/todaySales")
    public int todaySles(){
        return caisseService.todaysales();
    }

    // for admin
    @GetMapping("/totalSales")
    public Double totalSales(){
        return caisseService.totalsales();
    }

    //todayAdmin revenu
    @GetMapping("/AdminRevenu")
    public Double AdminRe(){
        return caisseService.adminRevenu();
    }

    //total revenu
    @GetMapping("/AdminTotalRevenu")
    public Double AdminRevenuetotal(){return caisseService.AdmeinRedvenuTotal();}
    //today sales vendeur
    @GetMapping("/VendeurSalesToday/{id}")
    public int vendeurS(@PathVariable String id){return caisseService.vendeurtodaysales(id);}
    @GetMapping("/VendeurSalesTotal/{id}")
    public int vendeurTotalSales(@PathVariable String id){return caisseService.vendeurtotalsales(id);}
    @GetMapping("/VendeurTodayR/{id}")
    public double vendeurTRevenu(@PathVariable String id){return caisseService.todayRevenueV(id);}

    @GetMapping("/VendeurTotalRevenu/{id}")
    public double vendeurRevenuT(@PathVariable String id){return caisseService.totalRevenue(id);}


}

