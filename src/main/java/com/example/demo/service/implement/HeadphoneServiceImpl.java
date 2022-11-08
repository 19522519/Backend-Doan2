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

import com.example.demo.dto.HeadPhoneDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.HeadPhoneEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.HeadPhoneRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.HeadPhoneService;

@Service
public class HeadPhoneServiceImpl implements HeadPhoneService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    HeadPhoneRepository HeadPhoneRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public HeadPhoneEntity saveNewHeadPhone(HeadPhoneDto HeadPhoneDto) {
        HeadPhoneEntity HeadPhoneEntity = new HeadPhoneEntity();
        ProductEntity productEntity = new ProductEntity();

        HeadPhoneEntity.setId(HeadPhoneDto.getHeadPhoneId());
        productEntity.setName(HeadPhoneDto.getHeadPhonename());
        productEntity.setDescription(HeadPhoneDto.getDescription());
        productEntity.setPrice(HeadPhoneDto.getPrice());
        productEntity.setDiscount(HeadPhoneDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(HeadPhoneDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setColor(HeadPhoneDto.getColor());
        HeadPhoneEntity.setType(HeadPhoneDto.getType());
        HeadPhoneEntity.setFrequency(HeadPhoneDto.getFrequency());
        
        productEntity.setWeight(HeadPhoneDto.getWeight());
        productEntity.setInsurance(HeadPhoneDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(HeadPhoneDto.getQuantity());
        productEntity.setIsDeleted(false);
        HeadPhoneEntity.setIsDeleted(false);

        productEntity.setThumbnail(HeadPhoneDto.getThumbnail());

        // MultipartFile multipartFile = HeadPhoneDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(HeadPhoneDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        HeadPhoneEntity.setProduct(productEntity);
        HeadPhoneRepository.save(HeadPhoneEntity);
        return HeadPhoneEntity;
    }

    @Override
    public HeadPhoneDto toDto(HeadPhoneEntity HeadPhoneEntity) {
        HeadPhoneDto HeadPhoneDto = new HeadPhoneDto();

        HeadPhoneDto.setHeadPhoneId(HeadPhoneEntity.getId());
        HeadPhoneDto.setHeadPhonename(HeadPhoneEntity.getProduct().getName());
        HeadPhoneDto.setDescription(HeadPhoneEntity.getProduct().getDescription());
        HeadPhoneDto.setPrice(HeadPhoneEntity.getProduct().getPrice());
        HeadPhoneDto.setDiscount(HeadPhoneEntity.getProduct().getDiscount());
        HeadPhoneDto.setBrand(HeadPhoneEntity.getProduct().getBrand().getName());
        HeadPhoneDto.setType(HeadPhoneEntity.getType());
        HeadPhoneDto.setColor(HeadPhoneEntity.getProduct().getColor());
        HeadPhoneDto.setFrequency(HeadPhoneEntity.getFrequency());
       
        HeadPhoneDto.setWeight(HeadPhoneEntity.getProduct().getWeight());
        HeadPhoneDto.setInsurance(HeadPhoneEntity.getProduct().getInsurance());
        HeadPhoneDto.setCreateDate(HeadPhoneEntity.getProduct().getCreateDate());
        HeadPhoneDto.setThumbnail(HeadPhoneEntity.getProduct().getThumbnail());
        HeadPhoneDto.setQuantity(HeadPhoneEntity.getProduct().getQuantity());

        return HeadPhoneDto;
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
    public List<HeadPhoneDto> findAllHeadPhone() {
        List<HeadPhoneDto> HeadPhoneDtos = new ArrayList<>();
        for (HeadPhoneEntity HeadPhoneEntity : HeadPhoneRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            HeadPhoneDtos.add(toDto(HeadPhoneEntity));
        }
        return HeadPhoneDtos;
    }

    @Override
    public void deleteHeadPhone(Integer id) {
        HeadPhoneEntity HeadPhone = HeadPhoneRepository.findById(id).get();
        HeadPhone.setIsDeleted(true);
        HeadPhoneRepository.save(HeadPhone);

        Integer productId = HeadPhone.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public HeadPhoneEntity saveExistHeadPhone(HeadPhoneDto HeadPhoneDto) {
        HeadPhoneEntity HeadPhoneEntity = HeadPhoneRepository.findById(HeadPhoneDto.getHeadPhoneId()).get();
        ProductEntity productEntity = productRepository.findById(HeadPhoneEntity.getProduct().getId()).get();

        productEntity.setName(HeadPhoneDto.getHeadPhonename());
        productEntity.setDescription(HeadPhoneDto.getDescription());
        productEntity.setPrice(HeadPhoneDto.getPrice());
        productEntity.setDiscount(HeadPhoneDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(HeadPhoneDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setColor(HeadPhoneDto.getColor());
        HeadPhoneEntity.setType(HeadPhoneDto.getType());
        HeadPhoneEntity.setFrequency(HeadPhoneDto.getFrequency());
        productEntity.setWeight(HeadPhoneDto.getWeight());
        productEntity.setInsurance(HeadPhoneDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(HeadPhoneDto.getQuantity());
        productEntity.setIsDeleted(false);
        HeadPhoneEntity.setIsDeleted(false);

        productEntity.setThumbnail(HeadPhoneDto.getThumbnail());

        // MultipartFile multipartFile = HeadPhoneDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(HeadPhoneDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        HeadPhoneEntity.setProduct(productEntity);
        HeadPhoneRepository.save(HeadPhoneEntity);
        return HeadPhoneEntity;
    }

    @Override
    public HeadPhoneDto editHeadPhone(Integer id) {
        HeadPhoneEntity HeadPhoneEntity = HeadPhoneRepository.findById(id).get();
        HeadPhoneDto HeadPhoneDto = toDto(HeadPhoneEntity);
        return HeadPhoneDto;
    }
}



