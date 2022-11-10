package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity, Integer> {
    LaptopEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<LaptopEntity> findByIsDeletedIsFalse(Pageable pageable);

    LaptopEntity findByIdAndIsDeletedIsFalse(Integer id);

    List<LaptopEntity> findByIsDeletedIsFalseOrderByIdAsc();
}
