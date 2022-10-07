package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;

public interface LaptopService {
    LaptopEntity toEntity(LaptopDto laptopDto);

    LaptopDto toDto(LaptopEntity laptopEntity);

    List<LaptopDto> findAllLaptop();
}
