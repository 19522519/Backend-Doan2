package com.example.demo.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.CartItemService;
import com.example.demo.service.ShoppingCartService;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Override
    public void createCartItem(AppUser appUser, ProductEntity productEntity, String productType) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProduct(productEntity);
        cartItemEntity.setAppUser(appUser);
        cartItemEntity.setIsDeleted(false);
        cartItemEntity.setQuantity(1);
        cartItemEntity.setProductType(productType);

        cartItemRepository.save(cartItemEntity);
    }

    @Override
    public List<CartItemEntity> getSumLaptops() {
        return cartItemRepository.findByProductTypeAndIsDeletedIsTrue("laptop");
    }

    @Override
    public List<CartItemEntity> getSumPCs() {
        return cartItemRepository.findByProductTypeAndIsDeletedIsTrue("pc");
    }

    @Override
    public List<CartItemEntity> getSumScreens() {
        return cartItemRepository.findByProductTypeAndIsDeletedIsTrue("screen");
    }

    @Override
    public List<CartItemEntity> getSumMouses() {
        return cartItemRepository.findByProductTypeAndIsDeletedIsTrue("mouse");
    }

    @Override
    public List<CartItemEntity> getSumKeyboards() {
        return cartItemRepository.findByProductTypeAndIsDeletedIsTrue("keyboard");
    }

    @Override
    public void deleteCartItemBasedOnOrder(Integer orderId) {
        List<CartItemEntity> cartItemEntities = cartItemRepository.findAll();

        for (CartItemEntity cartItemEntity : cartItemEntities) {
            if (cartItemEntity.getOrder().getId() == orderId)
                cartItemRepository.delete(cartItemEntity);
        }
    }
}
