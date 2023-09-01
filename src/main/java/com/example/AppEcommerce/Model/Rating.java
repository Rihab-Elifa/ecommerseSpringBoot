package com.example.AppEcommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Rating {
    @Id
    private String id;
    private int NumR;

    private String u;

    private String a;


    public Rating(int numR,String u, String a) {
        this.NumR = numR;
        this.u = u;
        this.a=a;
    }

}
