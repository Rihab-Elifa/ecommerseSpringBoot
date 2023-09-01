package com.example.AppEcommerce.Controller;

import com.example.AppEcommerce.Dto.CommandeDto;
import com.example.AppEcommerce.Model.Commander;
import com.example.AppEcommerce.Model.Notif;
import com.example.AppEcommerce.Repository.CommandeRepository;
import com.example.AppEcommerce.Repository.NotifyRepository;
import com.example.AppEcommerce.Service.CaisseService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class NotifyController {
    @Autowired
    NotifyRepository notifyRepository;
    @Autowired
    CommandeRepository commandeRepository;


    @Autowired
    CaisseService caisseService;



    //add commmand
    @PostMapping(value="/AjoutCommande",consumes = "application/json")
    private ResponseEntity<String>  commander(@RequestBody CommandeDto commande)throws FirebaseMessagingException {
        return caisseService.addCaisse( commande);
    }
        /*commandeRepository.save(commande);
        try {
            caisseService.sendCaisseNotif(commande.getId());
            ResponseEntity.ok("Notification sent successfully");
        } catch (FirebaseMessagingException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification: " + e.getMessage());
        }*/



    //add notifcation for vendor
    @PostMapping(value="/addNotify")
    private String addd(@RequestBody  Notif notify){
       // notifyRepository.save(notify);
        return notify.getId();
    }


    @GetMapping("/caisseNotif/{id}")
    public ResponseEntity<String> sendNotification(@PathVariable String id) {
        try {
            caisseService.sendCaisseNotif(id);
            return ResponseEntity.ok("Notification sent successfully");
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification: " + e.getMessage());
        }

    }
    //get list notification
    @GetMapping(value = "/getListNotification")
    public List<Notif> getNotificationList() {
        return notifyRepository.findAll();
    }



    //getdetail de commande sur notification
    @GetMapping(value = "/getCaisse/{id}")
    public Commander getCaisseById(@PathVariable String id) {
        return caisseService.getCaisseById(id);
    }


    @GetMapping(value = "/addDeliveryToCaisse/{idCaisse}/{idDelivery}")
    public void addDeliveryToCaisse(@PathVariable String idCaisse,
                                    @PathVariable String idDelivery) throws FirebaseMessagingException {
        caisseService.addDeliveryToCaisse(idCaisse, idDelivery);
    }

    @GetMapping(value = "/caisseListClient/{id}")
    public List<Commander> caisseListClient(@PathVariable String id) {
        return caisseService.caisseListClient(id);
    }
    @GetMapping(value = "/caisseListVendor/{id}")
    public List<Commander> caisseListVendor(@PathVariable String id) {
        return caisseService.caisseListVendor(id);
    }
    @GetMapping(value = "/cancelOrderByVendor/{id}")
    public void cancelOrderByVendor(@PathVariable String id) throws FirebaseMessagingException {
        caisseService.CancelByVendor(id);
    }
    @GetMapping(value = "/AcceptOrderByVendor/{id}")
    public void AcceptOrderByVendor(@PathVariable String id) throws FirebaseMessagingException {
        caisseService.AcceptByVendor(id);
    }


    @GetMapping(value = "/cancelOrderByClient/{id}")
    public void cancelOrderByClient(@PathVariable String id) throws FirebaseMessagingException {
        caisseService.CancelByClient(id);
    }
    //get list du commande
    @GetMapping("/getAll")
    public List<Commander> getList() {
        return commandeRepository.findAll();
    }
    //update sold
    @PutMapping("/UpdateSolde/{id}")
    public void UpdateS(@PathVariable String id ,@RequestBody int i){
        caisseService.UpdateSolde(id,i);
    }
    @PutMapping("/Etat/{id}")
    public void Update(@PathVariable String id ){
        caisseService.etat(id);
    }

    //modifier le solde et compteur la somme de solde ajouter pour le sous admin pour le sous admin
    @PutMapping("/UpdateSoldeSousAdmin/{id}/{id2}")
    public void SoldesousAdmin(@PathVariable String id ,@PathVariable String id2,@RequestBody int i){
        caisseService.UpdateSoldeSousAdmin(id,id2,i);
    }
    @PutMapping("/PagesSoldeSousAdmin/{id}/{id2}")
    public void PagesSoldesousAdmin(@PathVariable String id ,@PathVariable String id2,@RequestBody int i){
        caisseService.PageUpdateSolde(id,id2,i);
    }
    //reset le compteur pour le sous admin
    @PutMapping("/Reset/{id}")
    public void Reset(@PathVariable String id ){
        caisseService.Reset(id);
    }

    }

