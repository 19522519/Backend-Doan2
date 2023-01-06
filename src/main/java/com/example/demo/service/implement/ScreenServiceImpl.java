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
    ScreenRepository screenRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public ScreenEntity saveNewScreen(ScreenDto ScreenDto, MultipartFile img) {
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
        productEntity.setInventory(ScreenDto.getQuantity());
        productEntity.setIsDeleted(false);
        ScreenEntity.setIsDeleted(false);
        CategoryEntity categoryEntity = categoryRepository.findByName("Screen");

        productEntity.setCategory(categoryEntity);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

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
        screenRepository.save(ScreenEntity);
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
        ScreenDto.setInventory(ScreenEntity.getProduct().getInventory());

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
    public List<ScreenDto> findFiveScreen() {
        List<ScreenDto> ScreenDtos = new ArrayList<>();
        for (ScreenEntity ScreenEntity : screenRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id")))) {
            ScreenDtos.add(toDto(ScreenEntity));
        }
        return ScreenDtos;
    }

    @Override
    public List<ScreenDto> findAllScreen() {
        List<ScreenDto> ScreenDtos = new ArrayList<>();
        for (ScreenEntity ScreenEntity : screenRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id")))) {
            ScreenDtos.add(toDto(ScreenEntity));
        }
        return ScreenDtos;
    }


    @Override
    public void deleteScreen(Integer id) {
        ScreenEntity Screen = screenRepository.findById(id).get();
        Screen.setIsDeleted(true);
        screenRepository.save(Screen);

        Integer productId = Screen.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public ScreenEntity saveExistScreen(ScreenDto ScreenDto, MultipartFile img) {
        ScreenEntity ScreenEntity = screenRepository.findById(ScreenDto.getScreenId()).get();
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
        productEntity.setInventory(ScreenDto.getQuantity());
        productEntity.setIsDeleted(false);
        ScreenEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

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
        screenRepository.save(ScreenEntity);
        return ScreenEntity;
    }

    @Override
    public ScreenDto editScreen(Integer id) {
        ScreenEntity ScreenEntity = screenRepository.findById(id).get();
        ScreenDto ScreenDto = toDto(ScreenEntity);
        return ScreenDto;
    }

    @Override
    public Page<ScreenDto> findScreenPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<ScreenDto> ScreenDtos = new ArrayList<>();
        List<ScreenDto> list = new ArrayList<>();

        for (ScreenEntity ScreenEntity : screenRepository.findByIsDeletedIsFalseOrderByIdAsc()) {
            if (ScreenEntity != null)
                ScreenDtos.add(toDto(ScreenEntity));
        }

        if (ScreenDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ScreenDtos.size());
            list = ScreenDtos.subList(startItem, toIndex);
        }

        Page<ScreenDto> ScreenPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ScreenDtos.size());
        return ScreenPage;
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
    public ScreenDto screenDetail(Integer id) {
        ScreenEntity screenEntity = screenRepository.findById(id).get();
        ScreenDto screenDto = toDto(screenEntity);
        return screenDto;
    }
}