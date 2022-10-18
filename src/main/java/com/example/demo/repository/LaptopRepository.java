package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LaptopEntity;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity, Integer> {
    List<LaptopEntity> findAll();

    @Query(value = "select e from LaptopEntity e limit?1", nativeQuery = true)
    List<LaptopEntity> findAll(Integer limit);
}
