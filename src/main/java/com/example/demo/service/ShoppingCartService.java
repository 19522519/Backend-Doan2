package com.example.demo.service;

import java.util.Collection;

import com.example.demo.dto.CartItemDto;

public interface ShoppingCartService {
    void add(CartItemDto cartItemDto);

    void remove(Integer id);

    CartItemDto update(Integer id, Integer quantity);

    void clear();

    Collection<CartItemDto> getAllItems();

    Integer getCount();

    Double getTotalPrice();

    void addToCart(Integer id);
}
