package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.MouseEntity;
import com.example.demo.entity.ProductEntity;


@Repository
public interface MouseRepository extends JpaRepository<MouseEntity, Integer> {
    MouseEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<MouseEntity> findByIsDeletedIsFalse(Pageable pageable);
}

