package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.MouseDto;
import com.example.demo.entity.MouseEntity;

public interface MouseService {
    MouseEntity saveNewMouse(MouseDto MouseDto, MultipartFile img);

    MouseDto toDto(MouseEntity MouseEntity);

    List<MouseDto> findAllMouse();

    void deleteMouse(Integer id);

    MouseDto editMouse(Integer id);

    MouseEntity saveExistMouse(MouseDto MouseDto, MultipartFile img);

    // Collection Page
    Page<MouseDto> findMousePaginated(Pageable pageable);

    MouseDto mouseDetail(Integer id);
}
