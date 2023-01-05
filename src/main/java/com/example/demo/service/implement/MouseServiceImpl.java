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

import com.example.demo.dto.MouseDto;
import com.example.demo.entity.BrandEntity;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.MouseEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MouseRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.MouseService;

@Service
public class MouseServiceImpl implements MouseService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    MouseRepository MouseRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public MouseEntity saveNewMouse(MouseDto MouseDto, MultipartFile img) {
        MouseEntity MouseEntity = new MouseEntity();
        ProductEntity productEntity = new ProductEntity();

        MouseEntity.setId(MouseDto.getMouseId());
        productEntity.setName(MouseDto.getMousename());
        productEntity.setDescription(MouseDto.getDescription());
        productEntity.setPrice(MouseDto.getPrice());
        productEntity.setDiscount(MouseDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(MouseDto.getBrand());
        productEntity.setBrand(brandEntity);
        MouseEntity.setConnection(MouseDto.getConnection());
        MouseEntity.setLed(MouseDto.getLed());
        MouseEntity.setBattery(MouseDto.getBattery());
        productEntity.setWeight(MouseDto.getWeight());
        productEntity.setInsurance(MouseDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());

        productEntity.setQuantity(MouseDto.getQuantity());
        productEntity.setInventory(MouseDto.getQuantity());
        productEntity.setIsDeleted(false);
        MouseEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = MouseDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(MouseDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        MouseEntity.setProduct(productEntity);
        MouseRepository.save(MouseEntity);
        return MouseEntity;
    }

    @Override
    public MouseDto toDto(MouseEntity MouseEntity) {
        MouseDto MouseDto = new MouseDto();

        MouseDto.setMouseId(MouseEntity.getId());
        MouseDto.setMousename(MouseEntity.getProduct().getName());
        MouseDto.setDescription(MouseEntity.getProduct().getDescription());
        MouseDto.setPrice(MouseEntity.getProduct().getPrice());
        MouseDto.setDiscount(MouseEntity.getProduct().getDiscount());
        MouseDto.setBrand(MouseEntity.getProduct().getBrand().getName());
        MouseDto.setConnection(MouseEntity.getConnection());
        MouseDto.setLed(MouseEntity.getLed());
        MouseDto.setBattery(MouseEntity.getBattery());
        MouseDto.setWeight(MouseEntity.getProduct().getWeight());
        MouseDto.setInsurance(MouseEntity.getProduct().getInsurance());
        MouseDto.setCreateDate(MouseEntity.getProduct().getCreateDate());
        MouseDto.setThumbnail(MouseEntity.getProduct().getThumbnail());
        MouseDto.setQuantity(MouseEntity.getProduct().getQuantity());

        return MouseDto;
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
    public List<MouseDto> findAllMouse() {
        List<MouseDto> MouseDtos = new ArrayList<>();
        for (MouseEntity MouseEntity : MouseRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            MouseDtos.add(toDto(MouseEntity));
        }
        return MouseDtos;
    }

    @Override
    public void deleteMouse(Integer id) {
        MouseEntity Mouse = MouseRepository.findById(id).get();
        Mouse.setIsDeleted(true);
        MouseRepository.save(Mouse);

        Integer productId = Mouse.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public MouseEntity saveExistMouse(MouseDto MouseDto, MultipartFile img) {
        MouseEntity MouseEntity = MouseRepository.findById(MouseDto.getMouseId()).get();
        ProductEntity productEntity = productRepository.findById(MouseEntity.getProduct().getId()).get();

        productEntity.setName(MouseDto.getMousename());
        productEntity.setDescription(MouseDto.getDescription());
        productEntity.setPrice(MouseDto.getPrice());
        productEntity.setDiscount(MouseDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(MouseDto.getBrand());
        productEntity.setBrand(brandEntity);
        MouseEntity.setConnection(MouseDto.getConnection());
        MouseEntity.setLed(MouseDto.getLed());
        MouseEntity.setBattery(MouseDto.getBattery());
        productEntity.setWeight(MouseDto.getWeight());
        productEntity.setInsurance(MouseDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());

        productEntity.setQuantity(MouseDto.getQuantity());
        productEntity.setInventory(MouseDto.getQuantity());
        
        productEntity.setIsDeleted(false);
        MouseEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = MouseDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(MouseDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        MouseEntity.setProduct(productEntity);
        MouseRepository.save(MouseEntity);
        return MouseEntity;
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
    public MouseDto editMouse(Integer id) {
        MouseEntity MouseEntity = MouseRepository.findById(id).get();
        MouseDto MouseDto = toDto(MouseEntity);
        return MouseDto;
    }

    @Override
    public Page<MouseDto> findMousePaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<MouseDto> mouseDtos = new ArrayList<>();
        List<MouseDto> list = new ArrayList<>();

        for (MouseEntity mouseEntity : MouseRepository.findByIsDeletedIsFalseOrderByIdAsc()) {
            if (mouseEntity != null)
                mouseDtos.add(toDto(mouseEntity));
        }

        if (mouseDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, mouseDtos.size());
            list = mouseDtos.subList(startItem, toIndex);
        }

        Page<MouseDto> mousePage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), mouseDtos.size());
        return mousePage;
    }

    @Override
    public MouseDto mouseDetail(Integer id) {
        MouseEntity mouseEntity = MouseRepository.findById(id).get();
        MouseDto mouseDto = toDto(mouseEntity);
        return mouseDto;
    }
}
