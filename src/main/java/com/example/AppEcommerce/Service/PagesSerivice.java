package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.MessageResponse;
import com.example.AppEcommerce.Dto.PagesDto;
import com.example.AppEcommerce.Impl.PagesServicesImpl;
import com.example.AppEcommerce.Model.Article;
import com.example.AppEcommerce.Model.File;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.User;
import com.example.AppEcommerce.Repository.ArticleRepository;
import com.example.AppEcommerce.Repository.FileRepository;
import com.example.AppEcommerce.Repository.PagesRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PagesSerivice implements PagesServicesImpl {
    @Autowired
    private PagesRepository pagesRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;
    @Autowired ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public ResponseEntity<String>  addPage(String idUser, PagesDto pagesDto, MultipartFile fileProfile, MultipartFile fileCouverture) throws IOException {
        File imageProfile = new File(fileProfile.getOriginalFilename(),fileProfile.getContentType(),fileProfile.getBytes());
        File imageCouverture = new File(fileCouverture.getOriginalFilename(),fileCouverture.getContentType(),fileCouverture.getBytes());
        User user = userRepository.findById(idUser).orElseThrow(null);
        PageVendor pages  =new PageVendor(pagesDto.getTitle(),pagesDto.getAddress(),imageProfile,imageCouverture,pagesDto.getEmail(),pagesDto.getPhone(),pagesDto.getPostalCode(),pagesDto.getActivity(),pagesDto.getRegion(),pagesDto.getLongitude(),pagesDto.getLatitude());
        pagesRepository.save(pages);
        user.getPages().add(pages);
        userRepository.save(user);
        return ResponseEntity.ok().body("{\"pageId\": \"" + pages.getId() + "\"}");



    }

    @Override
    public ResponseEntity<String> editPage(String id, PagesDto pagesDto ) {


        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
        PageVendor pages = pagesRepository.findById(pagesDto.getId()).orElseThrow(null);

        List<Article> articles = articleService.findByPage(pages.getId());
        pages.setTitle(pagesDto.getTitle());
        pages.setAddress(pagesDto.getAddress());

        pages.setPhone(pagesDto.getPhone());
        pages.setPostalCode(pagesDto.getPostalCode());
        pages.setEmail(pagesDto.getEmail());
        pages.setActivity(pagesDto.getActivity());
        pages.setRegion(pagesDto.getRegion());
        pages.setLongitude(pagesDto.getLongitude());
        pages.setLatitude(pagesDto.getLatitude());
        PageVendor pages1 = pagesRepository.save(pages);
        articles.forEach(article -> {
            article.setPage(pages1);
            articleRepository.save(article);
        });
        user.getPages().forEach(pages2 -> {
            if(pages2.getId().equals(pages1.getId())){
                pages2.setEmail(pages1.getEmail());
                pages2.setAddress(pages1.getAddress());
                pages2.setPhone(pages1.getPhone());
                pages2.setTitle(pages1.getTitle());
                pages2.setPostalCode(pages1.getPostalCode());
                pages2.setActivity(pages1.getActivity());
                pages2.setRegion(pages1.getRegion());
                pages2.setLongitude(pages1.getLongitude());
                pages2.setLatitude(pages1.getLatitude());

                userRepository.save(user);
            }
        });


        return ResponseEntity.ok().body("{\"pageId\": \"" + pages1.getId() + "\"}");
    }


    @Override
    public ResponseEntity<?> editPhotoProfile(String id, MultipartFile fileProfile)throws IOException {
        File imageProfile = new File(fileProfile.getOriginalFilename(), fileProfile.getContentType(), fileProfile.getBytes());
        PageVendor page = pagesRepository.findById(id).orElseThrow();
        fileRepository.save(imageProfile);
        page.setImageProfile(imageProfile);
        pagesRepository.save(page);
        return ResponseEntity.ok(new MessageResponse("images Profile edit successufuly "));
    }

    @Override
    public ResponseEntity<?> editPhotoCouverture(String id, MultipartFile fileCouverture)throws IOException {
        File imageCouverture = new File(fileCouverture.getOriginalFilename(), fileCouverture.getContentType(), fileCouverture.getBytes());
        PageVendor page = pagesRepository.findById(id).orElseThrow();
        fileRepository.save(imageCouverture);
        page.setImageCouverture(imageCouverture);
        pagesRepository.save(page);
        return ResponseEntity.ok(new MessageResponse("images Couverture edit successufuly "));
    }
    @Override
    public void deletePage(String idPage, String idUser) {
        Optional<User> user = userRepository.findById(idUser);
        PageVendor page = pagesRepository.findById(idPage).orElseThrow();
        Iterator<PageVendor> iterator = user.get().getPages().iterator();
        while(iterator.hasNext()){
            PageVendor page1 = iterator.next();
            if(page1.getId().equals(page.getId())){
                iterator.remove();
                userRepository.save(user.get());
                pagesRepository.delete(page1);
            }
        }
    }


}
