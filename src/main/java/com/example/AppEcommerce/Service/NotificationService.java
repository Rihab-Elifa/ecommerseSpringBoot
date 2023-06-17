package com.example.AppEcommerce.Service;

import com.example.AppEcommerce.Dto.ArticleCaisse;
import com.example.AppEcommerce.Enum.Role;
import com.example.AppEcommerce.Model.Commander;
import com.example.AppEcommerce.Model.Device;
import com.example.AppEcommerce.Model.PageVendor;
import com.example.AppEcommerce.Model.*;
import com.example.AppEcommerce.Repository.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    FcmService fcmService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommandeRepository caisseRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    UserServicesI userServicesI;

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleService articleService;

    public Device addDevice(String token, String email){
        Optional<Device> optDevice =deviceRepository.findOneByToken(token);
        if(optDevice.isPresent()){
            return null;
        }
        User user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            Device device =new Device(token);
            device.setUser(user);
            return deviceRepository.save(device);


        }
        return null;
    }
    public List<Device> devices(String id){
        List<Device> devices = deviceRepository.findAll();
        List<Device> devices1 = new ArrayList<>();
        devices.forEach(device -> {
            if(device.getUser().getId().equals(id)){
                devices1.add(device);
            }
        });
        return devices1;
    }
    public List<Device> findAllByUserIdIn(Set<String> usersIds){
        List<Device> devices = deviceRepository.findAll();
        List<Device> devices1 = new ArrayList<>();
        usersIds.forEach(id ->{
            devices.forEach(device ->{
                if(device.getUser().getId().equals(id)){
                    devices1.add(device);
                }
            });
        });
        return devices1;
    }


    public void sendPushNotification(String idCaisse) throws FirebaseMessagingException {
        Commander caisse = caisseRepository.findById(idCaisse)
                .orElseThrow(()-> new NoSuchElementException("caisse not found with ID"+ idCaisse));
        User user=userServicesI.getUserByPage(caisse.getIdVendor());

        List<Device> devices = devices(user.getId());
        AtomicReference<String> body = new AtomicReference<>("You have received a new order ");

        Notif notif = new Notif("New Order", body.get(), caisse.getId());
        Notif savedNotif = notificationRepository.save(notif);
        System.out.println(savedNotif);
        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        Notification notification = Notification.builder()
                .setTitle(savedNotif.getTitle())
                .setBody(savedNotif.getBody())
                .build();
        Map<String, String> data = new HashMap<>();
        data.put("idCaisse", idCaisse);
        if(devices.size() > 0) {
            devices.forEach(d -> {
                try {

                    Message message = Message.builder()
                            .setNotification(notification)
                            .putAllData(data)
                            .setToken(d.getToken())
                            .build();
                    messaging.send(message);
                    System.out.println(message);
                    System.out.println(d);

                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    public void sendDeliveriesPushNotificationLocal(String idCaisse) throws FirebaseMessagingException {
        Commander caisse = caisseRepository.findById(idCaisse)
                .orElseThrow(()-> new NoSuchElementException("caisse not found with ID"+ idCaisse));
        ArticleCaisse premierArticleCaisse = caisse.getArticles().get(0);
        Article premierArticle = articleRepository.findById(premierArticleCaisse.getId())
                .<NoSuchElementException>orElseThrow(() -> new NoSuchElementException("article not found with ID" + premierArticleCaisse.getId()));
        PageVendor page = premierArticle.getPage();

        List<User> deliveries = userRepository.findDeliveryByRole(Role.DELIVERY);

        List<User> deliveriesWithSold = new ArrayList<>();
        deliveries.forEach(del ->{
            if(del.getSold() > 0 && del.isEnLigne()){
                deliveriesWithSold.add(del);
            }
        });
        double MAX_DISTANCE = 10.0;
        List<User> nearbyDeliveries = deliveriesWithSold.stream()
                .filter(delivery -> articleService.calculate(delivery.getLatitude(), delivery.getLongitude(), page.getLatitude(), page.getLongitude()) <= MAX_DISTANCE)
                .limit(10)
                .collect(Collectors.toList());
        Set<String> deliveriesIds = new HashSet<>();
        nearbyDeliveries.forEach(delivery -> {
            deliveriesIds.add(delivery.getId());
        });
        List<Device> devices =findAllByUserIdIn(deliveriesIds);
        AtomicReference<String> body = new AtomicReference<>("You have received a new delivery ");
        Notif notif = new Notif("New Delivery", body.get(), caisse.getId());
        Notif savedNotif = notificationRepository.save(notif);
        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        Notification notification = Notification.builder()
                .setTitle(savedNotif.getTitle())
                .setBody(savedNotif.getBody())
                .build();
        Map<String, String> data = new HashMap<>();
        data.put("idCaisse", idCaisse);
        if(devices.size() > 0) {
            devices.forEach(d -> {
                try {
                    Message message = Message.builder()
                            .setNotification(notification)
                            .putAllData(data)
                            .setToken(d.getToken())
                            .build();
                    messaging.send(message);

                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
