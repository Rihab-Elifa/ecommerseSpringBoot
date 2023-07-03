package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.ArticleCaisse;
import com.example.AppEcommerce.Dto.CommandeDto;
import com.example.AppEcommerce.Enum.Role;
import com.example.AppEcommerce.Enum.Status;
import com.example.AppEcommerce.Impl.CaisseServiceImp;
import com.example.AppEcommerce.Model.*;
import com.example.AppEcommerce.Repository.CommandeRepository;
import com.example.AppEcommerce.Repository.NotificationRepository;
import com.example.AppEcommerce.Repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CaisseService implements CaisseServiceImp {

    @Autowired
    private CommandeRepository caisseRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private  UserServicesI userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public String addCaisse(CommandeDto caisseDto) throws FirebaseMessagingException{
        Commander caisse =new Commander(caisseDto.getIdSender(),caisseDto.getAddress(),caisseDto.getStreetAddress(),caisseDto.getPhone(),caisseDto.getSelectedTime(),caisseDto.getDescription(), caisseDto.getIdVendor(), caisseDto.getSubtotal(),caisseDto.getFrais(),caisseDto.getTotalPrice(),caisseDto.getArticles(), LocalDate.now());
        Commander caisse2 = caisseRepository.save(caisse);
        sendCaisseNotif(caisse2.getId());
        return caisse2.getId();
    }
    @Override
    public void sendCaisseNotif(String id) throws FirebaseMessagingException {
        Commander caisse = caisseRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("caisse not found with ID"+ id));

        notificationService.sendPushNotification(id);
        notificationService.sendDeliveriesPushNotificationLocal(caisse.getId());
    }
    @Override
    public Commander getCaisseById(String id) {
        return caisseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Caisse not found with id " + id));
    }
    @Override
    public List<Commander> caisseListClient(String idSender){
        return caisseRepository.findByidSender(idSender);
    }
    @Override
    public List<Commander> findByVender(String id) {
        List<Commander> c=new ArrayList<>();
        List<Commander>cc=caisseRepository.findAll();
      for(Commander comd :cc){
          if(comd.getIdVendor().equals(id)){
              c.add(comd);
          }
      }
        return c;
    }
    @Override
    public List<Commander> caisseListVendor(String idVendor){
        List<Commander> newCaisse = new ArrayList<>();
        caisseRepository.findByidVendor(idVendor).forEach(caisse -> {

                newCaisse.add(caisse);

        });
        return newCaisse;
    }
    @Override
    public void addDeliveryToCaisse(String idCaisse, String idDelivery) throws FirebaseMessagingException {
        Commander caisse = getCaisseById(idCaisse);
        caisse.setIdDelivery(idDelivery);
        caisseRepository.save(caisse);

    }
    @Override
    public void CancelByVendor(String idCaisse) throws FirebaseMessagingException {
        Commander caisse = getCaisseById(idCaisse);
        caisse.setStatus(Status.CANCEL);
        caisseRepository.save(caisse);

    }
    @Override
    public void AcceptByVendor(String idCaisse)  {
        Commander caisse = getCaisseById(idCaisse);
        caisse.setStatus(Status.IN_PREPARATION);
        caisseRepository.save(caisse);
    }
    @Override
    public void CancelByClient(String idCaisse) throws FirebaseMessagingException {
        Commander caisse = getCaisseById(idCaisse);
        caisse.setStatus(Status.CANCEL);
        caisseRepository.save(caisse);

    }
    //set sold client
    @Override
    public void UpdateSolde(String id, int i){
        User u=userRepository.findById(id).orElseThrow();
        u.setSold(i+u.getSold());
        userRepository.save(u);
    }
    //update solde pour sous admin
    @Override
    public void UpdateSoldeSousAdmin(String id, String id2, int i){
        User u=userRepository.findById(id).orElseThrow();
        User u2=userRepository.findById(id2).orElseThrow();
        u.setSold(i+u.getSold());
        //Pour charger tous les soldes ajouter pour le sous admin
        u2.setCompteurC(u2.getCompteurC()+i);
        u2.setT(u2.getT()+i*0.01);
        userRepository.save(u);
        userRepository.save(u2);

    }
    //update solde pour sous admin (carger le page)
    @Override
    public void PageUpdateSolde(String id, String id2, int i){
        User u=userService.getUserByPage(id);
        User u2=userRepository.findById(id2).orElseThrow();
        if(u!=null){
        u.setSold(i+u.getSold());
        //Pour charger tous les soldes ajouter pour le sous admin
        u2.setCompteurC(u2.getCompteurC()+i);
        u2.setT(u2.getT()+i*0.01);
        userRepository.save(u);
        userRepository.save(u2);}

    }
    //reset sous admin
    @Override
    public void Reset(String id){
        User u=userRepository.findById(id).orElseThrow();
        u.setCompteurC(0);
        userRepository.save(u);


    }

    //update etat
    @Override
    public void UpdateSolde(String id){
        User u=userRepository.findById(id).orElseThrow();
        u.setEtat(false);
        userRepository.save(u);
    }
    //today sales
    @Override
    public int todaysales() {
        List<Commander> c= caisseRepository.findAll();
        int revenue=0;
        int prix=0;
        RevenueDate revenueDate = null;
        List<ArticleCaisse> articleCaisses = null;
        for (Commander cc: c) {
            if (cc.getDate() != null) {
                if (cc.getStatus() == Status.DELIVERED && cc.getDate().equals(LocalDate.now())) {

                    for (ArticleCaisse article : cc.getArticles()) {
                        prix = Integer.parseInt(article.getPrix());
                        int i = prix * article.getQuantity();
                        int revenu = i;
                        revenue += revenu;


                    }

                }
            }
            else {return 0;}
        }


        return revenue;
    }
    //total sales
    @Override
    public Double totalsales() {
        List<Commander> c = caisseRepository.findAll();
        double revenue = 0;
        double prix = 0;
        for (Commander cc : c) {

            if (cc.getStatus() == Status.DELIVERED ){

                for (ArticleCaisse article : cc.getArticles()) {
                    prix = Double.parseDouble(article.getPrix());

                    revenue += prix * article.getQuantity();

                }
            }
        }
        return revenue;
    }

    //nbre de livrison today
    @Override
    public int revenuLivreurT() {
        List<User> users = userRepository.findAll();
        RevenueDate todayRevenue = null;
        int revenue=0;
        int i=0;

        for (User u : users) {
            if (u.getRole()== Role.DELIVERY) {


                for (RevenueDate revenueDate : u.getRevenueDates()) {
                    if  (revenueDate.getDate()!=null) {
                        if (revenueDate.getDate().equals(LocalDate.now())) {
                            revenue += revenueDate.getRevenue();
                            i++;
                        }
                    }
                }

            }

        }
        return  i;
    }

    //nbre de livraison total
    @Override
    public int revenuLivreur() {
        List<User> users = userRepository.findAll();
        int i=0;
        for (User u : users) {
            if (u.getRole()== Role.DELIVERY) {
                if (u.getRevenueDates() != null) {
                    for (RevenueDate revenueDate : u.getRevenueDates()) {
                        i++;
                    }

                }
            }

        }
        return  i;
    }
    //nbre de article today
    @Override
    public int SalesARTToday() {
        List<Commander> c= caisseRepository.findAll();
        int i=0;
        List<ArticleCaisse> articleCaisses = null;
        for (Commander cc: c) {
            if (cc.getDate() != null) {
                if (cc.getStatus() == Status.DELIVERED && cc.getDate().equals(LocalDate.now())) {

                    for (ArticleCaisse article : cc.getArticles()) {

                        i = i + article.getQuantity();

                    }
                }
            }
        }
        return i;
    }
    //nbre article total
    @Override
    public int SalesART() {
        List<Commander> c= caisseRepository.findAll();
        int i=0;
        List<ArticleCaisse> articleCaisses = null;
        for (Commander cc: c) {
            if (cc.getStatus() == Status.DELIVERED ) {
                for (ArticleCaisse article : cc.getArticles()) {
                    i  = i + article.getQuantity();

                }
            }
        }
        return i;
    }
    //revenu admin today
    @Override
    public double adminRevenu(){
        double somme=0;
        somme=SalesARTToday()*1000+revenuLivreurT()*500;
        return somme;
    }
    //revenu total du admin


    @Override
    public double AdmeinRedvenuTotal(){
        double somme=0;
        somme=SalesART()*1000+revenuLivreur()*500;
        return somme;
    }
     ///vendeur
    //today sales
    @Override
    public int vendeurtodaysales(String id) {
    List<Commander> c= findByVender(id);
    int revenue=0;
    int prix=0;
    RevenueDate revenueDate = null;
    List<ArticleCaisse> articleCaisses = null;
    for (Commander cc: c) {
        if (cc.getDate() != null) {
            if (cc.getStatus() == Status.DELIVERED && cc.getDate().equals(LocalDate.now())) {

                for (ArticleCaisse article : cc.getArticles()) {
                    prix = Integer.parseInt(article.getPrix());
                    int i = prix * article.getQuantity();
                    int revenu = i;
                    revenue += revenu;


                }

            }
        }

    }


    return   revenue ;
}
//vendeur total sales
@Override
public int vendeurtotalsales(String id) {
    List<Commander> c= findByVender(id);

    int revenue=0;
    int prix=0;
    RevenueDate revenueDate = null;
    List<ArticleCaisse> articleCaisses = null;
    for (Commander cc: c) {

            if (cc.getStatus() == Status.DELIVERED ) {

                for (ArticleCaisse article : cc.getArticles()) {
                    prix = Integer.parseInt(article.getPrix());
                    int i = prix * article.getQuantity();
                    int revenu = i;
                    revenue += revenu;


                }

            }

    }


    return revenue;
}
//today revenu du venduer
@Override
public double todayRevenueV(String id) {
    List<Commander> c= caisseRepository.findByidVendor(id);
    int revenue=0;
    int prix=0;
    int i=0;

    List<ArticleCaisse> articleCaisses = null;
    for (Commander cc: c) {
        if (cc.getDate() != null) {
            if (cc.getStatus() == Status.DELIVERED && cc.getDate().equals(LocalDate.now())) {

                for (ArticleCaisse article : cc.getArticles()) {
                    prix = Integer.parseInt(article.getPrix());
                    revenue += (prix * article.getQuantity());
                    i +=article.getQuantity();


                }

            }
        }
        else {return 0;}
    }
    return revenue-(i*1000);
}


//total revenu vendeur

    @Override
    public double totalRevenue(String id) {
        List<Commander> c= caisseRepository.findByidVendor(id);
        int revenue=0;
        int prix=0;
        int i=0;

        List<ArticleCaisse> articleCaisses = null;
        for (Commander cc: c) {
                if (cc.getStatus() == Status.DELIVERED ) {
                    for (ArticleCaisse article : cc.getArticles()) {
                        prix = Integer.parseInt(article.getPrix());
                        revenue += (prix * article.getQuantity());
                         i +=article.getQuantity();


                    }

                }

        }
        return revenue-(i*1000);
    }


}
