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

import com.example.demo.dto.PcDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.PcEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PcRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.PcService;

@Service
public class PcServiceImpl implements PcService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    PcRepository PcRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public PcEntity saveNewPC(PcDto PcDto) {
        PcEntity PcEntity = new PcEntity();
        ProductEntity productEntity = new ProductEntity();

        PcEntity.setId(PcDto.getPCId());
        productEntity.setName(PcDto.getPCname());
        productEntity.setDescription(PcDto.getDescription());
        productEntity.setPrice(PcDto.getPrice());
        productEntity.setDiscount(PcDto.getDiscount());
        PcEntity.setCpu(PcDto.getCpu());
        PcEntity.setGraphicsCard(PcDto.getGraphicsCard());
        PcEntity.setRam(PcDto.getRam());
        PcEntity.setSsd(PcDto.getSsd());
        PcEntity.setMainboard(PcDto.getMainboard());
        PcEntity.setCase(PcDto.getCase());
        PcEntity.setCooler(PcDto.getCooler());
        productEntity.setInsurance(PcDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(PcDto.getQuantity());
        productEntity.setIsDeleted(false);
        PcEntity.setIsDeleted(false);

        productEntity.setThumbnail(PcDto.getThumbnail());

        // MultipartFile multipartFile = PcDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(PcDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        PcEntity.setProduct(productEntity);
        PcRepository.save(PcEntity);
        return PcEntity;
    }

    @Override
    public PcDto toDto(PcEntity PcEntity) {
        PcDto PcDto = new PcDto();

        PcDto.setPCId(PcEntity.getId());
        PcDto.setPCname(PcEntity.getProduct().getName());
        PcDto.setDescription(PcEntity.getProduct().getDescription());
        PcDto.setPrice(PcEntity.getProduct().getPrice());
        PcDto.setDiscount(PcEntity.getProduct().getDiscount());
        PcDto.setCpu(PcEntity.getCpu());
        PcDto.setGraphicsCard(PcEntity.getGraphicsCard());
        PcDto.setRam(PcEntity.getRam());
        PcDto.setSsd(PcEntity.getSsd());
        PcDto.setMainboard(PcEntity.getMainboard());
        PcDto.setCase(PcEntity.getCase());
        PcDto.setCooler(PcEntity.getCooler());
        PcDto.setInsurance(PcEntity.getProduct().getInsurance());
        PcDto.setCreateDate(PcEntity.getProduct().getCreateDate());
        PcDto.setThumbnail(PcEntity.getProduct().getThumbnail());
        PcDto.setQuantity(PcEntity.getProduct().getQuantity());

        return PcDto;
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
    public List<PcDto> findAllPc() {
        List<PcDto> PcDtos = new ArrayList<>();
        for (PcEntity PcEntity : PcRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            PcDtos.add(toDto(PcEntity));
        }
        return PcDtos;
    }

    @Override
    public void deletePc(Integer id) {
        PcEntity Pc = PcRepository.findById(id).get();
        Pc.setIsDeleted(true);
        PcRepository.save(Pc);

        Integer productId = Pc.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public PcEntity saveExistPc(PcDto PcDto) {
        PcEntity PcEntity = PcRepository.findById(PcDto.getPCId()).get();
        ProductEntity productEntity = productRepository.findById(PcEntity.getProduct().getId()).get();

        productEntity.setName(PcDto.getPCname());
        productEntity.setDescription(PcDto.getDescription());
        productEntity.setPrice(PcDto.getPrice());
        productEntity.setDiscount(PcDto.getDiscount());
        PcEntity.setCpu(PcDto.getCpu());
        PcEntity.setGraphicsCard(PcDto.getGraphicsCard());
        PcEntity.setRam(PcDto.getRam());
        PcEntity.setSsd(PcDto.getSsd());
        PcEntity.setMainboard(PcDto.getMainboard());
        PcEntity.setCase(PcDto.getCase());
        PcEntity.setCooler(PcDto.getCooler());
        productEntity.setInsurance(PcDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        
        productEntity.setQuantity(PcDto.getQuantity());
        productEntity.setIsDeleted(false);
        PcEntity.setIsDeleted(false);

        productEntity.setThumbnail(PcDto.getThumbnail());

        // MultipartFile multipartFile = PcDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(PcDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        PcEntity.setProduct(productEntity);
        PcRepository.save(PcEntity);
        return PcEntity;
    }

    @Override
    public PcDto editPc(Integer id) {
        PcEntity PcEntity = PcRepository.findById(id).get();
        PcDto PcDto = toDto(PcEntity);
        return PcDto;
    }

    
}

