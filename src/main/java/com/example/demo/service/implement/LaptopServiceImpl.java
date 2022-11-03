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

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public LaptopEntity saveNewLaptop(LaptopDto laptopDto) {
        LaptopEntity laptopEntity = new LaptopEntity();
        ProductEntity productEntity = new ProductEntity();

        laptopEntity.setId(laptopDto.getLaptopId());
        productEntity.setName(laptopDto.getLaptopName());
        productEntity.setDescription(laptopDto.getDescription());
        productEntity.setPrice(laptopDto.getPrice());
        productEntity.setDiscount(laptopDto.getDiscount());
        laptopEntity.setCpu(laptopDto.getCpu());
        laptopEntity.setGraphicsCard(laptopDto.getGraphicsCard());
        laptopEntity.setRam(laptopDto.getRam());
        laptopEntity.setStorageDrive(laptopDto.getStorageDrive());
        laptopEntity.setScreen(laptopDto.getScreen());
        laptopEntity.setOperatingSystem(laptopDto.getOperatingSystem());
        laptopEntity.setCommunicationPort(laptopDto.getCommunicationPort());
        laptopEntity.setKeyboard(laptopDto.getKeyboard());
        laptopEntity.setBattery(laptopDto.getKeyboard());
        productEntity.setSize(laptopDto.getSize());
        productEntity.setWeight(laptopDto.getWeight());
        laptopEntity.setLan(laptopDto.getLan());
        productEntity.setInsurance(laptopDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        BrandEntity brandEntity = brandRepository.findByName(laptopDto.getBrand());
        productEntity.setBrand(brandEntity);
        CategoryEntity categoryEntity = categoryRepository.findByName(laptopDto.getCategory());
        productEntity.setCategory(categoryEntity);
        productEntity.setQuantity(laptopDto.getQuantity());
        productEntity.setIsDeleted(false);
        laptopEntity.setIsDeleted(false);

        productEntity.setThumbnail(laptopDto.getThumbnail());

        // MultipartFile multipartFile = laptopDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(laptopDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        laptopEntity.setProduct(productEntity);
        laptopRepository.save(laptopEntity);
        return laptopEntity;
    }

    @Override
    public LaptopDto toDto(LaptopEntity laptopEntity) {
        LaptopDto laptopDto = new LaptopDto();

        laptopDto.setLaptopId(laptopEntity.getId());
        laptopDto.setLaptopName(laptopEntity.getProduct().getName());
        laptopDto.setDescription(laptopEntity.getProduct().getDescription());
        laptopDto.setPrice(laptopEntity.getProduct().getPrice());
        laptopDto.setDiscount(laptopEntity.getProduct().getDiscount());
        laptopDto.setCpu(laptopEntity.getCpu());
        laptopDto.setGraphicsCard(laptopEntity.getGraphicsCard());
        laptopDto.setRam(laptopEntity.getRam());
        laptopDto.setStorageDrive(laptopEntity.getStorageDrive());
        laptopDto.setScreen(laptopEntity.getScreen());
        laptopDto.setOperatingSystem(laptopEntity.getOperatingSystem());
        laptopDto.setCommunicationPort(laptopEntity.getCommunicationPort());
        laptopDto.setKeyboard(laptopEntity.getKeyboard());
        laptopDto.setBattery(laptopEntity.getBattery());
        laptopDto.setSize(laptopEntity.getProduct().getSize());
        laptopDto.setWeight(laptopEntity.getProduct().getWeight());
        laptopDto.setLan(laptopEntity.getLan());
        laptopDto.setInsurance(laptopEntity.getProduct().getInsurance());
        laptopDto.setCreateDate(laptopEntity.getProduct().getCreateDate());
        laptopDto.setThumbnail(laptopEntity.getProduct().getThumbnail());
        laptopDto.setBrand(laptopEntity.getProduct().getBrand().getName());
        laptopDto.setCategory(laptopEntity.getProduct().getCategory().getName());
        laptopDto.setQuantity(laptopEntity.getProduct().getQuantity());

        return laptopDto;
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
    public List<LaptopDto> findAllLaptop() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            laptopDtos.add(toDto(laptopEntity));
        }
        return laptopDtos;
    }

    @Override
    public void deleteLaptop(Integer id) {
        LaptopEntity laptop = laptopRepository.findById(id).get();
        laptop.setIsDeleted(true);
        laptopRepository.save(laptop);

        Integer productId = laptop.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public LaptopEntity saveExistLaptop(LaptopDto laptopDto) {
        LaptopEntity laptopEntity = laptopRepository.findById(laptopDto.getLaptopId()).get();
        ProductEntity productEntity = productRepository.findById(laptopEntity.getProduct().getId()).get();

        productEntity.setName(laptopDto.getLaptopName());
        productEntity.setDescription(laptopDto.getDescription());
        productEntity.setPrice(laptopDto.getPrice());
        productEntity.setDiscount(laptopDto.getDiscount());
        laptopEntity.setCpu(laptopDto.getCpu());
        laptopEntity.setGraphicsCard(laptopDto.getGraphicsCard());
        laptopEntity.setRam(laptopDto.getRam());
        laptopEntity.setStorageDrive(laptopDto.getStorageDrive());
        laptopEntity.setScreen(laptopDto.getScreen());
        laptopEntity.setOperatingSystem(laptopDto.getOperatingSystem());
        laptopEntity.setCommunicationPort(laptopDto.getCommunicationPort());
        laptopEntity.setKeyboard(laptopDto.getKeyboard());
        laptopEntity.setBattery(laptopDto.getKeyboard());
        productEntity.setSize(laptopDto.getSize());
        productEntity.setWeight(laptopDto.getWeight());
        laptopEntity.setLan(laptopDto.getLan());
        productEntity.setInsurance(laptopDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());
        BrandEntity brandEntity = brandRepository.findByName(laptopDto.getBrand());
        productEntity.setBrand(brandEntity);
        CategoryEntity categoryEntity = categoryRepository.findByName(laptopDto.getCategory());
        productEntity.setCategory(categoryEntity);
        productEntity.setQuantity(laptopDto.getQuantity());
        productEntity.setInventory(laptopDto.getInventory());
        productEntity.setIsDeleted(false);
        laptopEntity.setIsDeleted(false);

        productEntity.setThumbnail(laptopDto.getThumbnail());

        laptopEntity.setProduct(productEntity);
        laptopRepository.save(laptopEntity);
        return laptopEntity;
    }

    @Override
    public LaptopDto editLaptop(Integer id) {
        LaptopEntity laptopEntity = laptopRepository.findById(id).get();
        LaptopDto laptopDto = toDto(laptopEntity);
        return laptopDto;
    }

    @Override
    public List<LaptopDto> findAllLaptopGaming() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        List<LaptopEntity> laptopEntities = new ArrayList<>();

        CategoryEntity categoryEntity = categoryRepository.findByName("Gaming");
        List<ProductEntity> productEntities = productRepository
                .findByCategoryAndIsDeletedIsFalseOrderByCreateDateDesc(categoryEntity);
        for (ProductEntity productEntity : productEntities)
            laptopEntities.add(laptopRepository.findByProductAndIsDeletedIsFalse(productEntity));

        for (LaptopEntity laptopEntity : laptopEntities) {
            if (laptopEntity != null)
                laptopDtos.add(toDto(laptopEntity));
        }

        List<LaptopDto> lists = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            lists.add(laptopDtos.get(i - 1));

        return lists;
    }

    // Baeldung
    @Override
    public Page<LaptopDto> findLaptopGamingPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<LaptopDto> laptopDtos = new ArrayList<>();
        List<LaptopEntity> laptopEntities = new ArrayList<>();
        List<LaptopDto> list = new ArrayList<>();

        CategoryEntity categoryEntity = categoryRepository.findByName("Gaming");
        List<ProductEntity> productEntities = productRepository
                .findByCategoryAndIsDeletedIsFalseOrderByCreateDateDesc(categoryEntity);
        for (ProductEntity productEntity : productEntities)
            laptopEntities.add(laptopRepository.findByProductAndIsDeletedIsFalse(productEntity));

        for (LaptopEntity laptopEntity : laptopEntities) {
            if (laptopEntity != null)
                laptopDtos.add(toDto(laptopEntity));
        }

        if (laptopDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, laptopDtos.size());
            list = laptopDtos.subList(startItem, toIndex);
        }

        Page<LaptopDto> laptopPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), laptopDtos.size());
        return laptopPage;
    }

    // Baeldung
    @Override
    public Page<LaptopDto> findLaptopOfficePaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<LaptopDto> laptopDtos = new ArrayList<>();
        List<LaptopEntity> laptopEntities = new ArrayList<>();
        List<LaptopDto> list = new ArrayList<>();

        CategoryEntity categoryEntity = categoryRepository.findByName("Gaming");
        List<ProductEntity> productEntities = productRepository
                .findByCategoryAndIsDeletedIsFalseOrderByCreateDateDesc(categoryEntity);
        for (ProductEntity productEntity : productEntities)
            laptopEntities.add(laptopRepository.findByProductAndIsDeletedIsFalse(productEntity));

        for (LaptopEntity laptopEntity : laptopEntities) {
            if (laptopEntity != null)
                laptopDtos.add(toDto(laptopEntity));
        }

        List<LaptopDto> lists = new ArrayList<>();
        for (int i = 1; i <= 15; i++)
            lists.add(laptopDtos.get(i - 1));

        if (lists.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lists.size());
            list = lists.subList(startItem, toIndex);
        }

        Page<LaptopDto> laptopPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lists.size());
        return laptopPage;
    }

    @Override
    public List<LaptopDto> findAllLaptopVanPhong() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        List<LaptopEntity> laptopEntities = new ArrayList<>();

        CategoryEntity categoryEntity = categoryRepository.findByName("Office");
        List<ProductEntity> productEntities = productRepository
                .findByCategoryAndIsDeletedIsFalseOrderByCreateDateDesc(categoryEntity);
        for (ProductEntity productEntity : productEntities)
            laptopEntities.add(laptopRepository.findByProductAndIsDeletedIsFalse(productEntity));

        for (LaptopEntity laptopEntity : laptopEntities) {
            if (laptopEntity != null)
                laptopDtos.add(toDto(laptopEntity));
        }

        List<LaptopDto> lists = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            lists.add(laptopDtos.get(i - 1));

        return lists;
    }
}
