package com.example.AppEcommerce.Model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Data
public class Device {
    @Id
    private String id;
    @Lob
    private String token;
    @ManyToOne
    private User user;
    public Device(){}
    public Device(String token){this.token=token;}

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
