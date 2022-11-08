package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.ScreenDto;
import com.example.demo.entity.ScreenEntity;

public interface ScreenService {
    ScreenEntity saveNewScreen(ScreenDto ScreenDto);

    ScreenDto toDto(ScreenEntity ScreenEntity);

    List<ScreenDto> findAllScreen();

    void deleteScreen(Integer id);

    ScreenDto editScreen(Integer id);

    ScreenEntity saveExistScreen(ScreenDto ScreenDto);

}