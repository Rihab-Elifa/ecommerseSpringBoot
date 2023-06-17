package com.example.AppEcommerce.Repository;

import com.example.AppEcommerce.Model.Notif;
import com.example.AppEcommerce.Model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notif,String> {
}
