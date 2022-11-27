package com.example.demo.service.implement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.GamingChairDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.GamingChairEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.GamingChairRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.GamingChairService;

@Service
public class GamingChairServiceImpl implements GamingChairService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    GamingChairRepository GamingChairRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public GamingChairEntity saveNewGamingChair(GamingChairDto GamingChairDto, MultipartFile img) {
        GamingChairEntity GamingChairEntity = new GamingChairEntity();
        ProductEntity productEntity = new ProductEntity();

        GamingChairEntity.setId(GamingChairDto.getGamingChairId());
        productEntity.setName(GamingChairDto.getGamingChairname());
        productEntity.setDescription(GamingChairDto.getDescription());
        productEntity.setPrice(GamingChairDto.getPrice());
        productEntity.setDiscount(GamingChairDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(GamingChairDto.getBrand());
        productEntity.setBrand(brandEntity);
        productEntity.setInsurance(GamingChairDto.getInsurance());
        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(GamingChairDto.getQuantity());
        productEntity.setIsDeleted(false);
        GamingChairEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = GamingChairDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(GamingChairDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        GamingChairEntity.setProduct(productEntity);
        GamingChairRepository.save(GamingChairEntity);
        return GamingChairEntity;
    }

    @Override
    public GamingChairDto toDto(GamingChairEntity GamingChairEntity) {
        GamingChairDto GamingChairDto = new GamingChairDto();

        GamingChairDto.setGamingChairId(GamingChairEntity.getId());
        GamingChairDto.setGamingChairname(GamingChairEntity.getProduct().getName());
        GamingChairDto.setDescription(GamingChairEntity.getProduct().getDescription());
        GamingChairDto.setPrice(GamingChairEntity.getProduct().getPrice());
        GamingChairDto.setDiscount(GamingChairEntity.getProduct().getDiscount());
        GamingChairDto.setBrand(GamingChairEntity.getProduct().getBrand().getName());
        GamingChairDto.setInsurance(GamingChairEntity.getProduct().getInsurance());
        GamingChairDto.setCreateDate(GamingChairEntity.getProduct().getCreateDate());
        GamingChairDto.setThumbnail(GamingChairEntity.getProduct().getThumbnail());
        GamingChairDto.setQuantity(GamingChairEntity.getProduct().getQuantity());

        return GamingChairDto;
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
    public List<GamingChairDto> findAllGamingChair() {
        List<GamingChairDto> GamingChairDtos = new ArrayList<>();
        for (GamingChairEntity GamingChairEntity : GamingChairRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            GamingChairDtos.add(toDto(GamingChairEntity));
        }
        return GamingChairDtos;
    }

    @Override
    public void deleteGamingChair(Integer id) {
        GamingChairEntity GamingChair = GamingChairRepository.findById(id).get();
        GamingChair.setIsDeleted(true);
        GamingChairRepository.save(GamingChair);

        Integer productId = GamingChair.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public GamingChairEntity saveExistGamingChair(GamingChairDto GamingChairDto, MultipartFile img) {
        GamingChairEntity GamingChairEntity = GamingChairRepository.findById(GamingChairDto.getGamingChairId()).get();
        ProductEntity productEntity = productRepository.findById(GamingChairEntity.getProduct().getId()).get();

        productEntity.setName(GamingChairDto.getGamingChairname());
        productEntity.setDescription(GamingChairDto.getDescription());
        productEntity.setPrice(GamingChairDto.getPrice());
        productEntity.setDiscount(GamingChairDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(GamingChairDto.getBrand());
        productEntity.setBrand(brandEntity);
        
        productEntity.setInsurance(GamingChairDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(GamingChairDto.getQuantity());
        productEntity.setIsDeleted(false);
        GamingChairEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = GamingChairDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(GamingChairDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        GamingChairEntity.setProduct(productEntity);
        GamingChairRepository.save(GamingChairEntity);
        return GamingChairEntity;
    }

    public void saveFile(String image, MultipartFile img) {
        if (image != null) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public GamingChairDto editGamingChair(Integer id) {
        GamingChairEntity GamingChairEntity = GamingChairRepository.findById(id).get();
        GamingChairDto GamingChairDto = toDto(GamingChairEntity);
        return GamingChairDto;
    }
}




