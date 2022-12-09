package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.CartItemDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.ProductEntity;

public interface ShoppingCartService {
    void createCartItem(AppUser appUser, ProductEntity productEntity, String productType);

    List<CartItemDto> showListCartItems(AppUser appUser);

    Integer calculateTotalMoney(AppUser appUser);

    Integer countItemInCart(AppUser appUser);

    void removeCartItem(Integer cartItemId);

    void updateQuantityProduct(List<CartItemDto> cartItemDtos);
    // void add(CartItemDto cartItemDto);

    // void remove(Integer id);

    // CartItemDto update(Integer id, Integer quantity);

    // void clear();

    // Collection<CartItemDto> getAllItems();

    // Integer getCount();

    // Double getTotalPrice();

    // void addToCart(Integer id);
}
