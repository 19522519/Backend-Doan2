package com.example.demo.service;

import com.example.demo.dto.PcDto;
import com.example.demo.entity.PcEntity;

public interface PcService {
    PcEntity toEntity(PcDto pcDto);
}
