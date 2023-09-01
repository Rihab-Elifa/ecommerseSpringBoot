package com.example.AppEcommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ratings {
    private String id;

    private int NumR;

    private String u;

    private String a;
    public ratings(int numR, String a,String u) {
        this.NumR = numR;
        this.u = u;
        this.a=a;
    }

}
