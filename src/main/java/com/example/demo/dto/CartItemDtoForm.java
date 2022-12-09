package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class CartItemDtoForm {
    private List<CartItemDto> cartItemDtos;

    public List<CartItemDto> getCartItemDtos() {
        if (this.cartItemDtos == null) {
            this.cartItemDtos = new ArrayList<>();
        }
        return cartItemDtos;
    }

    public void setCartItemDtos(List<CartItemDto> cartItemDtos) {
        this.cartItemDtos = cartItemDtos;
    }
}
