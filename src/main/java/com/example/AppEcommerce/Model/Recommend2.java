package com.example.AppEcommerce.Model;

import com.example.AppEcommerce.Dto.Products;
import com.example.AppEcommerce.Dto.ratings;

import java.util.List;

public class Recommend2 {

    private List<ratings> ratings;
    private List<History> hp;

    public Recommend2(List<ratings> ratings, List<History> history) {
        this.ratings = ratings;
        this.hp = history;
    }

    public List<ratings> getRatings() {
        return ratings;
    }

    public List<History> getHp() {
        return hp;
    }

}
