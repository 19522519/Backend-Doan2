package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PcDto;
import com.example.demo.entity.PcEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.PcRepository;
import com.example.demo.service.PcService;

@Service
public class PcServiceImplement implements PcService {
    @Autowired
    PcRepository pcRepository;

    @Override
    public PcEntity toEntity(PcDto pcDto) {
        PcEntity pcEntity = new PcEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(pcDto.getName());
        productEntity.setDescription(pcDto.getName());

        pcEntity.setProduct(productEntity);
        pcRepository.save(pcEntity);

        return pcEntity;
    }
}
