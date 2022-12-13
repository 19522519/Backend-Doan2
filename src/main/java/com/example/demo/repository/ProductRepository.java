package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByCategoryAndIsDeletedIsFalseOrderByCreateDateDesc(CategoryEntity categoryEntity);

    ProductEntity findByIdAndIsDeletedIsFalse(Integer id);

    @Query("select p from ProductEntity p where concat(p.name, p.price) like %?1% and p.isDeleted=false")
    List<ProductEntity> search(String keyword);
}
