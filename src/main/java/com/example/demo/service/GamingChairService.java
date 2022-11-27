package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.GamingChairDto;
import com.example.demo.entity.GamingChairEntity;

public interface GamingChairService {
    GamingChairEntity saveNewGamingChair(GamingChairDto GamingChairDto ,  MultipartFile img);

    GamingChairDto toDto(GamingChairEntity GamingChairEntity);

    List<GamingChairDto> findAllGamingChair();

    void deleteGamingChair(Integer id);

    GamingChairDto editGamingChair(Integer id);

    GamingChairEntity saveExistGamingChair(GamingChairDto GamingChairDto ,  MultipartFile img);

}
