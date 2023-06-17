package com.example.AppEcommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
@Data
@AllArgsConstructor
public class Notification {

    @Id
    private String id;
    private String title ;
    @Column(length = 3000)
    private String body;

    private String idCaisse;

    public Notification(String title, String body,String idCaisse){
        this.title=title;
        this.body=body;
        this.idCaisse=idCaisse;

    }

    public Notification(String title, String body) {
        this.title=title;
        this.body=body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIdCaisse() {
        return idCaisse;
    }

    public void setIdCaisse(String idCaisse) {
        this.idCaisse = idCaisse;
    }
}
