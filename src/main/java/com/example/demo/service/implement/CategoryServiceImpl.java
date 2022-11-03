package com.example.demo.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<String> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<String> categoryNames = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities)
            categoryNames.add(categoryEntity.getName());
        return categoryNames;
    }
}
