package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GamingChairEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface GamingChairRepository extends JpaRepository<GamingChairEntity, Integer> {
    GamingChairEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<GamingChairEntity> findByIsDeletedIsFalse(Pageable pageable);
}
