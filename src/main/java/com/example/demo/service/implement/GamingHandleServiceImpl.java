package com.example.demo.service.implement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.GamingHandleDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.GamingHandleEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.GamingHandleRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.GamingHandleService;

@Service
public class GamingHandleServiceImpl implements GamingHandleService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    GamingHandleRepository GamingHandleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public GamingHandleEntity saveNewGamingHandle(GamingHandleDto GamingHandleDto) {
        GamingHandleEntity GamingHandleEntity = new GamingHandleEntity();
        ProductEntity productEntity = new ProductEntity();

        GamingHandleEntity.setId(GamingHandleDto.getGamingHandleId());
        productEntity.setName(GamingHandleDto.getGamingHandlename());
        productEntity.setDescription(GamingHandleDto.getDescription());
        productEntity.setPrice(GamingHandleDto.getPrice());
        productEntity.setDiscount(GamingHandleDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(GamingHandleDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setInsurance(GamingHandleDto.getInsurance());
        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(GamingHandleDto.getQuantity());
        productEntity.setIsDeleted(false);
        GamingHandleEntity.setIsDeleted(false);

        productEntity.setThumbnail(GamingHandleDto.getThumbnail());

        // MultipartFile multipartFile = GamingHandleDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(GamingHandleDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        GamingHandleEntity.setProduct(productEntity);
        GamingHandleRepository.save(GamingHandleEntity);
        return GamingHandleEntity;
    }

    @Override
    public GamingHandleDto toDto(GamingHandleEntity GamingHandleEntity) {
        GamingHandleDto GamingHandleDto = new GamingHandleDto();

        GamingHandleDto.setGamingHandleId(GamingHandleEntity.getId());
        GamingHandleDto.setGamingHandlename(GamingHandleEntity.getProduct().getName());
        GamingHandleDto.setDescription(GamingHandleEntity.getProduct().getDescription());
        GamingHandleDto.setPrice(GamingHandleEntity.getProduct().getPrice());
        GamingHandleDto.setDiscount(GamingHandleEntity.getProduct().getDiscount());
        GamingHandleDto.setBrand(GamingHandleEntity.getProduct().getBrand().getName());
        GamingHandleDto.setInsurance(GamingHandleEntity.getProduct().getInsurance());
        GamingHandleDto.setCreateDate(GamingHandleEntity.getProduct().getCreateDate());
        GamingHandleDto.setThumbnail(GamingHandleEntity.getProduct().getThumbnail());
        GamingHandleDto.setQuantity(GamingHandleEntity.getProduct().getQuantity());

        return GamingHandleDto;
    }

    // byte[] byteObjects;

    // private byte[] convertToBytes(MultipartFile file) {
    // try {
    // byteObjects = new byte[file.getBytes().length];
    // int i = 0;
    // for (byte b : file.getBytes()) {
    // byteObjects[i++] = b;
    // }
    // } catch (Exception ex) {
    // System.out.println("Image not upload successfully!");
    // }

    // return byteObjects;
    // }

    // Limit 10 product in a page
    @Override
    public List<GamingHandleDto> findAllGamingHandle() {
        List<GamingHandleDto> GamingHandleDtos = new ArrayList<>();
        for (GamingHandleEntity GamingHandleEntity : GamingHandleRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            GamingHandleDtos.add(toDto(GamingHandleEntity));
        }
        return GamingHandleDtos;
    }

    @Override
    public void deleteGamingHandle(Integer id) {
        GamingHandleEntity GamingHandle = GamingHandleRepository.findById(id).get();
        GamingHandle.setIsDeleted(true);
        GamingHandleRepository.save(GamingHandle);

        Integer productId = GamingHandle.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public GamingHandleEntity saveExistGamingHandle(GamingHandleDto GamingHandleDto) {
        GamingHandleEntity GamingHandleEntity = GamingHandleRepository.findById(GamingHandleDto.getGamingHandleId()).get();
        ProductEntity productEntity = productRepository.findById(GamingHandleEntity.getProduct().getId()).get();

        productEntity.setName(GamingHandleDto.getGamingHandlename());
        productEntity.setDescription(GamingHandleDto.getDescription());
        productEntity.setPrice(GamingHandleDto.getPrice());
        productEntity.setDiscount(GamingHandleDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(GamingHandleDto.getBrand());
        productEntity.setBrand(brandEntity);
        
        productEntity.setInsurance(GamingHandleDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(GamingHandleDto.getQuantity());
        productEntity.setIsDeleted(false);
        GamingHandleEntity.setIsDeleted(false);

        productEntity.setThumbnail(GamingHandleDto.getThumbnail());

        // MultipartFile multipartFile = GamingHandleDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(GamingHandleDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        GamingHandleEntity.setProduct(productEntity);
        GamingHandleRepository.save(GamingHandleEntity);
        return GamingHandleEntity;
    }

    @Override
    public GamingHandleDto editGamingHandle(Integer id) {
        GamingHandleEntity GamingHandleEntity = GamingHandleRepository.findById(id).get();
        GamingHandleDto GamingHandleDto = toDto(GamingHandleEntity);
        return GamingHandleDto;
    }
}



