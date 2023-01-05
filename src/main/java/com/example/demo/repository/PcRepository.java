package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PcEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface PcRepository extends JpaRepository<PcEntity, Integer> {
    PcEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<PcEntity> findByIsDeletedIsFalse(Pageable pageable);

    List<PcEntity> findByIsDeletedIsFalseOrderByIdAsc();

    PcEntity findByIdAndIsDeletedIsFalse(Integer id);
}
