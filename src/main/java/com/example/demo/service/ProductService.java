package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.ProductEntity;

public interface ProductService {
    Page<ProductEntity> findProductBasedOnKeyword(String keyword, Pageable pageable);
}
