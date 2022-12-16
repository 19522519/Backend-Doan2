package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findByIdAndIsDeletedIsFalse(Integer id);

    List<OrderEntity> findByAppUserAndIsDeletedIsFalse(AppUser appUser);

    List<OrderEntity> findByIsDeletedIsFalse();

    List<OrderEntity> findByIsDeletedIsFalseOrderByOrderDateDesc(Pageable pageable);
}
