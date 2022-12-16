package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;

public interface CartItemService {
    void createCartItem(AppUser appUser, ProductEntity productEntity, String productType);

    List<CartItemEntity> getSumLaptops();

    List<CartItemEntity> getSumPCs();

    List<CartItemEntity> getSumScreens();

    List<CartItemEntity> getSumMouses();

    List<CartItemEntity> getSumKeyboards();

    void deleteCartItemBasedOnOrder(Integer orderId);
}
