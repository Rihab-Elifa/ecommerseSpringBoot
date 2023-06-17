package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Dto.PagesDto;
import com.example.AppEcommerce.Enum.Activity;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Repository.PagesRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import com.example.AppEcommerce.Service.PagesSerivice;
import com.example.AppEcommerce.Service.UserServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/pages")
 public class PagesController {

    @Autowired
    private PagesSerivice pagesService;
    @Autowired
    private PagesRepository pagesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServicesI userServicesI;


    @PostMapping(value="/addPage/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})//bech dakhelou des données wa m3ahom images
    public String  addPage(@PathVariable String id , @RequestPart("page") PagesDto pagesDto, @RequestPart(name = "imageProfile", required = false) MultipartFile fileProfile, @RequestPart(name = "imageCouverture", required = false) MultipartFile fileCouverture) throws IOException {
        return pagesService.addPage(id,pagesDto,fileProfile,fileCouverture);
    }
    @GetMapping(value="/findAllPages")
    public List<PageVendor> findAllP(){
        return pagesRepository.findAll();
    }
    @GetMapping(value="/PagesUser/{id}")
    public List<PageVendor> findByUser(@PathVariable String id){
        return userServicesI.listPagesByUser(id);
    }
    @GetMapping(value="/detailPage/{id}")
    public Optional<PageVendor> detail(@PathVariable String id){
        return pagesRepository.findById(id);
    }
    //delete and update
    @PutMapping("/editPage/{id}")
    public String editPage(@PathVariable String id,@RequestBody PagesDto pagesDto){
        return pagesService.editPage(id,pagesDto);

    }
    @DeleteMapping(value = "/delete/{idUser}/{idPage}")
    public void deletePage(@PathVariable String idPage, @PathVariable String idUser) {
        pagesService.deletePage(idPage,idUser);
    }
    @PostMapping("/editProfile/{id}")
    public ResponseEntity<?> editPhotoProfile(@RequestPart(name = "imageProfile", required = false) MultipartFile fileProfile, @PathVariable String id) throws IOException {
        return pagesService.editPhotoProfile(id, fileProfile);
    }
    @PostMapping("/editCouverture/{id}")
    public ResponseEntity<?> editCouverture(@RequestPart(name = "imageCouverture", required = false) MultipartFile fileCouverture, @PathVariable String id) throws IOException {
        return pagesService.editPhotoCouverture(id,fileCouverture);
    }

    @GetMapping("/PagesBycategories/{cat}")
    public List<PageVendor> PagesByCatgorie(@PathVariable Activity cat){
        List<PageVendor> ll=pagesRepository.findByActivity(cat);
        return ll;
    }
    @GetMapping("/UserBypages/{id}")
    public String UserByPage(@PathVariable String id){
        User u =userServicesI.getUserByPage(id);
        return u.getId();
    }



}
