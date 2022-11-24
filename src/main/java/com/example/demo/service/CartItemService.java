package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;

public interface CartItemService {
    void createCartItem(AppUser appUser, ProductEntity productEntity, OrderEntity orderEntity);
}
