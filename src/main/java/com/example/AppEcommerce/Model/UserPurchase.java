package com.example.AppEcommerce.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPurchase {
    private String id;
    private String userId;
    private  List<History> productId;
}
