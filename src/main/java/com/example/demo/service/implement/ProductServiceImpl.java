package com.example.demo.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<ProductEntity> findProductBasedOnKeyword(String keyword, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<ProductEntity> productList = new ArrayList<>();
        List<ProductEntity> list = new ArrayList<>();

        // List<ProductEntity> l = productRepository.search(keyword);
        // System.out.println(l.size());

        for (ProductEntity productEntity : productRepository.search(keyword)) {
            if (productEntity != null)
                productList.add(productEntity);
        }
        // System.out.println(productList.size());

        if (productList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productList.size());
            list = productList.subList(startItem, toIndex);
        }

        Page<ProductEntity> searchPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize),
                productList.size());

        return searchPage;
    }
}
