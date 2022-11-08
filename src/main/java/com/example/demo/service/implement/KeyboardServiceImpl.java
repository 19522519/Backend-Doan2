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

import com.example.demo.dto.KeyBoardDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.KeyBoardEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.KeyBoardRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.KeyBoardService;

@Service
public class KeyBoardServiceImpl implements KeyBoardService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    KeyBoardRepository KeyBoardRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public KeyBoardEntity saveNewKeyBoard(KeyBoardDto KeyBoardDto) {
        KeyBoardEntity KeyBoardEntity = new KeyBoardEntity();
        ProductEntity productEntity = new ProductEntity();

        KeyBoardEntity.setId(KeyBoardDto.getKeyBoardId());
        productEntity.setName(KeyBoardDto.getKeyBoardname());
        productEntity.setDescription(KeyBoardDto.getDescription());
        productEntity.setPrice(KeyBoardDto.getPrice());
        productEntity.setDiscount(KeyBoardDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(KeyBoardDto.getBrand());
        productEntity.setBrand(brandEntity);
        KeyBoardEntity.setType(KeyBoardDto.getType());
        KeyBoardEntity.setLed(KeyBoardDto.getLed());
        
        productEntity.setWeight(KeyBoardDto.getWeight());
        productEntity.setInsurance(KeyBoardDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(KeyBoardDto.getQuantity());
        productEntity.setIsDeleted(false);
        KeyBoardEntity.setIsDeleted(false);

        productEntity.setThumbnail(KeyBoardDto.getThumbnail());

        // MultipartFile multipartFile = KeyBoardDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(KeyBoardDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        KeyBoardEntity.setProduct(productEntity);
        KeyBoardRepository.save(KeyBoardEntity);
        return KeyBoardEntity;
    }

    @Override
    public KeyBoardDto toDto(KeyBoardEntity KeyBoardEntity) {
        KeyBoardDto KeyBoardDto = new KeyBoardDto();

        KeyBoardDto.setKeyBoardId(KeyBoardEntity.getId());
        KeyBoardDto.setKeyBoardname(KeyBoardEntity.getProduct().getName());
        KeyBoardDto.setDescription(KeyBoardEntity.getProduct().getDescription());
        KeyBoardDto.setPrice(KeyBoardEntity.getProduct().getPrice());
        KeyBoardDto.setDiscount(KeyBoardEntity.getProduct().getDiscount());
        KeyBoardDto.setBrand(KeyBoardEntity.getProduct().getBrand().getName());
        KeyBoardDto.setType(KeyBoardEntity.getType());
        KeyBoardDto.setLed(KeyBoardEntity.getLed());
       
        KeyBoardDto.setWeight(KeyBoardEntity.getProduct().getWeight());
        KeyBoardDto.setInsurance(KeyBoardEntity.getProduct().getInsurance());
        KeyBoardDto.setCreateDate(KeyBoardEntity.getProduct().getCreateDate());
        KeyBoardDto.setThumbnail(KeyBoardEntity.getProduct().getThumbnail());
        KeyBoardDto.setQuantity(KeyBoardEntity.getProduct().getQuantity());

        return KeyBoardDto;
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
    public List<KeyBoardDto> findAllKeyBoard() {
        List<KeyBoardDto> KeyBoardDtos = new ArrayList<>();
        for (KeyBoardEntity KeyBoardEntity : KeyBoardRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            KeyBoardDtos.add(toDto(KeyBoardEntity));
        }
        return KeyBoardDtos;
    }

    @Override
    public void deleteKeyBoard(Integer id) {
        KeyBoardEntity KeyBoard = KeyBoardRepository.findById(id).get();
        KeyBoard.setIsDeleted(true);
        KeyBoardRepository.save(KeyBoard);

        Integer productId = KeyBoard.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public KeyBoardEntity saveExistKeyBoard(KeyBoardDto KeyBoardDto) {
        KeyBoardEntity KeyBoardEntity = KeyBoardRepository.findById(KeyBoardDto.getKeyBoardId()).get();
        ProductEntity productEntity = productRepository.findById(KeyBoardEntity.getProduct().getId()).get();

        productEntity.setName(KeyBoardDto.getKeyBoardname());
        productEntity.setDescription(KeyBoardDto.getDescription());
        productEntity.setPrice(KeyBoardDto.getPrice());
        productEntity.setDiscount(KeyBoardDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(KeyBoardDto.getBrand());
        productEntity.setBrand(brandEntity);
        KeyBoardEntity.setType(KeyBoardDto.getType());
        KeyBoardEntity.setLed(KeyBoardDto.getLed());
        productEntity.setWeight(KeyBoardDto.getWeight());
        productEntity.setInsurance(KeyBoardDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(KeyBoardDto.getQuantity());
        productEntity.setIsDeleted(false);
        KeyBoardEntity.setIsDeleted(false);

        productEntity.setThumbnail(KeyBoardDto.getThumbnail());

        // MultipartFile multipartFile = KeyBoardDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(KeyBoardDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        KeyBoardEntity.setProduct(productEntity);
        KeyBoardRepository.save(KeyBoardEntity);
        return KeyBoardEntity;
    }

    @Override
    public KeyBoardDto editKeyBoard(Integer id) {
        KeyBoardEntity KeyBoardEntity = KeyBoardRepository.findById(id).get();
        KeyBoardDto KeyBoardDto = toDto(KeyBoardEntity);
        return KeyBoardDto;
    }
}


