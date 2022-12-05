package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.HeadPhoneDto;
import com.example.demo.entity.HeadPhoneEntity;

public interface HeadphoneService {
    HeadPhoneEntity saveNewHeadPhone(HeadPhoneDto headPhoneDto, MultipartFile img);

    HeadPhoneDto toDto(HeadPhoneEntity headPhoneEntity);

    List<HeadPhoneDto> findAllHeadPhone();

    void deleteHeadPhone(Integer id);

    HeadPhoneDto editHeadPhone(Integer id);

    HeadPhoneEntity saveExistHeadPhone(HeadPhoneDto headPhoneDto, MultipartFile img);

}
