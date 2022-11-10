package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.KeyBoardDto;
import com.example.demo.entity.KeyBoardEntity;

public interface KeyBoardService {
    KeyBoardEntity saveNewKeyBoard(KeyBoardDto keyBoardDto);

    KeyBoardDto toDto(KeyBoardEntity keyBoardEntity);

    List<KeyBoardDto> findAllKeyBoard();

    void deleteKeyBoard(Integer id);

    KeyBoardDto editKeyBoard(Integer id);

    KeyBoardEntity saveExistKeyBoard(KeyBoardDto keyBoardDto);

}