package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingCart {
    private List<CartItemEntity> cartItems;

    public ShoppingCart(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public Boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public void removeCartItem(CartItemEntity cartItem) {
        cartItems.removeIf(item -> item.getId() == cartItem.getId());
    }

    public void clearItems() {
        cartItems.clear();
    }

    public int getItemCount() {
        return this.cartItems.size();
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }
}
