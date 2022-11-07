package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.PcDto;
import com.example.demo.entity.PcEntity;

public interface PcService {
    PcEntity saveNewPC(PcDto PcDto);

    PcDto toDto(PcEntity PcEntity);

    List<PcDto> findAllPc();

    void deletePc(Integer id);

    PcDto editPc(Integer id);

    PcEntity saveExistPc(PcDto PcDto);

}