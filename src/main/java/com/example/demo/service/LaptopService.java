package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;

public interface LaptopService {
    LaptopEntity saveNewLaptop(LaptopDto laptopDto, MultipartFile img);

    LaptopDto toDto(LaptopEntity laptopEntity);

    List<LaptopDto> findAllLaptop();

    void deleteLaptop(Integer id);

    LaptopDto detailLaptop(Integer id);

    LaptopEntity saveExistLaptop(LaptopDto laptopDto, MultipartFile img);

    // Index Page
    List<LaptopDto> findAllLaptopGaming();

    // Seller Page
    Page<LaptopDto> findLaptopPaginated(Pageable pageable);

    // Collection Page
    Page<LaptopDto> findLaptopGamingPaginated(Pageable pageable);

    // Collection Page
    Page<LaptopDto> findLaptopOfficePaginated(Pageable pageable);

    // Index Page
    List<LaptopDto> findAllLaptopVanPhong();
}
