package com.example.demo.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;

public interface LaptopService {
    LaptopEntity saveNewLaptop(LaptopDto laptopDto);

    LaptopDto toDto(LaptopEntity laptopEntity);

    List<LaptopDto> findAllLaptop();

    void deleteLaptop(Integer id);

    LaptopDto editLaptop(Integer id);

    LaptopEntity saveExistLaptop(LaptopDto laptopDto);

    List<LaptopDto> findAll();
}
