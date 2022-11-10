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

import com.example.demo.dto.ScreenDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ScreenEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ScreenService;

@Service
public class ScreenServiceImpl implements ScreenService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    ScreenRepository ScreenRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public ScreenEntity saveNewScreen(ScreenDto ScreenDto) {
        ScreenEntity ScreenEntity = new ScreenEntity();
        ProductEntity productEntity = new ProductEntity();

        ScreenEntity.setId(ScreenDto.getScreenId());
        productEntity.setName(ScreenDto.getScreenname());
        productEntity.setDescription(ScreenDto.getDescription());
        productEntity.setPrice(ScreenDto.getPrice());
        productEntity.setDiscount(ScreenDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(ScreenDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setSize(ScreenDto.getSize());
        ScreenEntity.setResolution(ScreenDto.getResolution());
        ScreenEntity.setFrequency(ScreenDto.getFrequency());
        ScreenEntity.setCommunicationPort(ScreenDto.getCommunicationPort());
        productEntity.setWeight(ScreenDto.getWeight());
        productEntity.setInsurance(ScreenDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(ScreenDto.getQuantity());
        productEntity.setIsDeleted(false);
        ScreenEntity.setIsDeleted(false);

        productEntity.setThumbnail(ScreenDto.getThumbnail());

        // MultipartFile multipartFile = ScreenDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(ScreenDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        ScreenEntity.setProduct(productEntity);
        ScreenRepository.save(ScreenEntity);
        return ScreenEntity;
    }

    @Override
    public ScreenDto toDto(ScreenEntity ScreenEntity) {
        ScreenDto ScreenDto = new ScreenDto();

        ScreenDto.setScreenId(ScreenEntity.getId());
        ScreenDto.setScreenname(ScreenEntity.getProduct().getName());
        ScreenDto.setDescription(ScreenEntity.getProduct().getDescription());
        ScreenDto.setPrice(ScreenEntity.getProduct().getPrice());
        ScreenDto.setDiscount(ScreenEntity.getProduct().getDiscount());
        ScreenDto.setBrand(ScreenEntity.getProduct().getBrand().getName());
        ScreenDto.setSize(ScreenEntity.getProduct().getSize());
        ScreenDto.setResolution(ScreenEntity.getResolution());
        ScreenDto.setRatio(ScreenEntity.getRatio());
        ScreenDto.setFrequency(ScreenEntity.getFrequency());
        ScreenDto.setWeight(ScreenEntity.getProduct().getWeight());
        ScreenDto.setInsurance(ScreenEntity.getProduct().getInsurance());
        ScreenDto.setCreateDate(ScreenEntity.getProduct().getCreateDate());
        ScreenDto.setThumbnail(ScreenEntity.getProduct().getThumbnail());
        ScreenDto.setQuantity(ScreenEntity.getProduct().getQuantity());

        return ScreenDto;
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
    public List<ScreenDto> findAllScreen() {
        List<ScreenDto> ScreenDtos = new ArrayList<>();
        for (ScreenEntity ScreenEntity : ScreenRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            ScreenDtos.add(toDto(ScreenEntity));
        }
        return ScreenDtos;
    }

    @Override
    public void deleteScreen(Integer id) {
        ScreenEntity Screen = ScreenRepository.findById(id).get();
        Screen.setIsDeleted(true);
        ScreenRepository.save(Screen);

        Integer productId = Screen.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public ScreenEntity saveExistScreen(ScreenDto ScreenDto) {
        ScreenEntity ScreenEntity = ScreenRepository.findById(ScreenDto.getScreenId()).get();
        ProductEntity productEntity = productRepository.findById(ScreenEntity.getProduct().getId()).get();

        productEntity.setName(ScreenDto.getScreenname());
        productEntity.setDescription(ScreenDto.getDescription());
        productEntity.setPrice(ScreenDto.getPrice());
        productEntity.setDiscount(ScreenDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(ScreenDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setSize(ScreenDto.getSize());
        ScreenEntity.setResolution(ScreenDto.getResolution());
        ScreenEntity.setFrequency(ScreenDto.getFrequency());
        ScreenEntity.setCommunicationPort(ScreenDto.getCommunicationPort());
        productEntity.setWeight(ScreenDto.getWeight());
        productEntity.setInsurance(ScreenDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(ScreenDto.getQuantity());
        productEntity.setIsDeleted(false);
        ScreenEntity.setIsDeleted(false);

        productEntity.setThumbnail(ScreenDto.getThumbnail());

        // MultipartFile multipartFile = ScreenDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(ScreenDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        ScreenEntity.setProduct(productEntity);
        ScreenRepository.save(ScreenEntity);
        return ScreenEntity;
    }

    @Override
    public ScreenDto editScreen(Integer id) {
        ScreenEntity ScreenEntity = ScreenRepository.findById(id).get();
        ScreenDto ScreenDto = toDto(ScreenEntity);
        return ScreenDto;
    }

    
}
