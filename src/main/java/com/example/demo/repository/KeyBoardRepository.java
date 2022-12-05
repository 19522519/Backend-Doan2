package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KeyBoardEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface KeyBoardRepository extends JpaRepository<KeyBoardEntity, Integer> {
    KeyBoardEntity findByProductAndIsDeletedIsFalse(ProductEntity productEntity);

    List<KeyBoardEntity> findByIsDeletedIsFalse(Pageable pageable);

    List<KeyBoardEntity> findByIsDeletedIsFalseOrderByIdAsc();

    KeyBoardEntity findByIdAndIsDeletedIsFalse(Integer id);
}
