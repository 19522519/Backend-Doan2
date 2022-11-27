package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.GamingHandleDto;
import com.example.demo.entity.GamingHandleEntity;

public interface GamingHandleService {
    GamingHandleEntity saveNewGamingHandle(GamingHandleDto GamingHandleDto ,  MultipartFile img);

    GamingHandleDto toDto(GamingHandleEntity GamingHandleEntity);

    List<GamingHandleDto> findAllGamingHandle();

    void deleteGamingHandle(Integer id);

    GamingHandleDto editGamingHandle(Integer id);

    GamingHandleEntity saveExistGamingHandle(GamingHandleDto GamingHandleDto ,  MultipartFile img) ;

}