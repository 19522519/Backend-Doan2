package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ScreenEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface ScreenRepository extends JpaRepository<ScreenEntity, Integer> {
    ScreenEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<ScreenEntity> findByIsDeletedIsFalse(Pageable pageable);
}
