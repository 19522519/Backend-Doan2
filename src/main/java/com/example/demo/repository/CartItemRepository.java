package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    CartItemEntity findByProductAndAppUserAndIsDeletedIsFalse(ProductEntity productEntity, AppUser appUser);

    CartItemEntity findByIdAndIsDeletedIsFalse(Integer cartItemId);

    List<CartItemEntity> findByIsDeletedIsFalse();
}
