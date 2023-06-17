package com.example.AppEcommerce.Impl;

import com.example.AppEcommerce.Dto.CommandeDto;
import com.example.AppEcommerce.Model.Commander;
import com.example.AppEcommerce.Model.RevenueDate;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface CaisseServiceImp {

    String addCaisse(CommandeDto caisseDto) throws FirebaseMessagingException;

    void sendCaisseNotif(String id) throws FirebaseMessagingException;


    Commander getCaisseById(String id);

    List<Commander> caisseListClient(String idSender);

    List<Commander> findByVender(String id);


    List<Commander> caisseListVendor(String idVendor);

    void addDeliveryToCaisse(String idCaisse, String idDelivery) throws FirebaseMessagingException;

    void CancelByVendor(String idCaisse) throws FirebaseMessagingException;

    void AcceptByVendor(String idCaisse);

    void CancelByClient(String idCaisse) throws FirebaseMessagingException;

    //set sold client
    void UpdateSolde(String id, int i);

    //update etat
    void UpdateSolde(String id);

    //today sales
    RevenueDate todaysales();

    //total sales
    Double totalsales();

    //revenu du livreur today
    int revenuLivreurT();

    int revenuLivreur();

    //nbre de article today
    int SalesARTToday();

    //nbre article total
    int SalesART();

    //revenu admin today


    //revenu admin today
    double adminRevenu();

    double AdmeinRedvenuTotal();

    ///vendeur
   //today sales
    RevenueDate vendeurtodaysales(String id);

    //vendeur total sales
    RevenueDate vendeurtotalsales(String id);



    double totalRevenue(String id);
}
