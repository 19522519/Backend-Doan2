package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.HeadPhoneEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface HeadPhoneRepository extends JpaRepository<HeadPhoneEntity, Integer> {
    HeadPhoneEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<HeadPhoneEntity> findByIsDeletedIsFalse(Pageable pageable);
}
