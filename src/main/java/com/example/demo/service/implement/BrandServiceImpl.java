package com.example.demo.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BrandEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<String> getAllBrands() {
        List<BrandEntity> brandEntities = brandRepository.findAll();
        List<String> brandNames = new ArrayList<>();
        for (BrandEntity brandEntity : brandEntities)
            brandNames.add(brandEntity.getName());
        return brandNames;
    }
}
