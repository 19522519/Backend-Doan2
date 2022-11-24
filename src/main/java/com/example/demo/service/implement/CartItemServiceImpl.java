package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public void createCartItem(AppUser appUser, ProductEntity productEntity, OrderEntity orderEntity) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setAppUser(appUser);
        cartItemEntity.setProduct(productEntity);
        cartItemEntity.setOrder(orderEntity);
        cartItemEntity.setIsDeleted(false);
        cartItemEntity.setQuantity(1);

        cartItemRepository.save(cartItemEntity);
    }
}
