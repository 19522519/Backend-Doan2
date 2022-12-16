package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    CartItemEntity findByIdAndIsDeletedIsFalse(Integer cartItemId);

    List<CartItemEntity> findByIsDeletedIsFalse();

    List<CartItemEntity> findByProductTypeAndIsDeletedIsTrue(String productType);

    List<CartItemEntity> findByOrderAndIsDeletedIsFalse(OrderEntity orderEntity);

    CartItemEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<CartItemEntity> findByAppUserAndIsDeletedIsFalse(AppUser appUser);

    List<CartItemEntity> findByAppUserAndIsDeletedIsTrue(AppUser appUser);
}
