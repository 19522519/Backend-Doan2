package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GamingHandleEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface GamingHandleRepository extends JpaRepository<GamingHandleEntity, Integer> {
    GamingHandleEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<GamingHandleEntity> findByIsDeletedIsFalse(Pageable pageable);
}
