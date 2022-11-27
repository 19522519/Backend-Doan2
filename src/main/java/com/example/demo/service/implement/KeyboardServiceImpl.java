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
public class KeyboardServiceImpl implements KeyBoardService {
    private final Integer pageSizeDefault = 5;

    @Autowired
    KeyBoardRepository keyBoardRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;
    // @Value("${upload.path}")
    // private String fileUpload;

    @Override
    public KeyBoardEntity saveNewKeyBoard(KeyBoardDto keyBoardDto, MultipartFile img) {
        KeyBoardEntity keyBoardEntity = new KeyBoardEntity();
        ProductEntity productEntity = new ProductEntity();

        keyBoardEntity.setId(keyBoardDto.getKeyBoardId());
        productEntity.setName(keyBoardDto.getKeyBoardname());
        productEntity.setDescription(keyBoardDto.getDescription());
        productEntity.setPrice(keyBoardDto.getPrice());
        productEntity.setDiscount(keyBoardDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(keyBoardDto.getBrand());
        productEntity.setBrand(brandEntity);
        keyBoardEntity.setType(keyBoardDto.getType());
        keyBoardEntity.setLed(keyBoardDto.getLed());

        productEntity.setWeight(keyBoardDto.getWeight());
        productEntity.setInsurance(keyBoardDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());

        productEntity.setQuantity(keyBoardDto.getQuantity());
        productEntity.setIsDeleted(false);
        keyBoardEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = KeyBoardDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(KeyBoardDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        keyBoardEntity.setProduct(productEntity);
        keyBoardRepository.save(keyBoardEntity);
        return keyBoardEntity;
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
    public KeyBoardDto toDto(KeyBoardEntity keyBoardEntity) {
        KeyBoardDto keyBoardDto = new KeyBoardDto();

        keyBoardDto.setKeyBoardId(keyBoardEntity.getId());
        keyBoardDto.setKeyBoardname(keyBoardEntity.getProduct().getName());
        keyBoardDto.setDescription(keyBoardEntity.getProduct().getDescription());
        keyBoardDto.setPrice(keyBoardEntity.getProduct().getPrice());
        keyBoardDto.setDiscount(keyBoardEntity.getProduct().getDiscount());
        keyBoardDto.setBrand(keyBoardEntity.getProduct().getBrand().getName());
        keyBoardDto.setType(keyBoardEntity.getType());
        keyBoardDto.setLed(keyBoardEntity.getLed());

        keyBoardDto.setWeight(keyBoardEntity.getProduct().getWeight());
        keyBoardDto.setInsurance(keyBoardEntity.getProduct().getInsurance());
        keyBoardDto.setCreateDate(keyBoardEntity.getProduct().getCreateDate());
        keyBoardDto.setThumbnail(keyBoardEntity.getProduct().getThumbnail());
        keyBoardDto.setQuantity(keyBoardEntity.getProduct().getQuantity());

        return keyBoardDto;
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
        List<KeyBoardDto> keyBoardDtos = new ArrayList<>();
        for (KeyBoardEntity keyBoardEntity : keyBoardRepository.findByIsDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")))) {
            keyBoardDtos.add(toDto(keyBoardEntity));
        }
        return keyBoardDtos;
    }

    @Override
    public void deleteKeyBoard(Integer id) {
        KeyBoardEntity KeyBoard = keyBoardRepository.findById(id).get();
        KeyBoard.setIsDeleted(true);
        keyBoardRepository.save(KeyBoard);

        Integer productId = KeyBoard.getProduct().getId();
        ProductEntity productEntity = productRepository.findById(productId).get();
        productEntity.setIsDeleted(true);
        productRepository.save(productEntity);
    }

    @Override
    public KeyBoardEntity saveExistKeyBoard(KeyBoardDto keyBoardDto , MultipartFile img) {
        KeyBoardEntity keyBoardEntity = keyBoardRepository.findById(keyBoardDto.getKeyBoardId()).get();
        ProductEntity productEntity = productRepository.findById(keyBoardEntity.getProduct().getId()).get();

        productEntity.setName(keyBoardDto.getKeyBoardname());
        productEntity.setDescription(keyBoardDto.getDescription());
        productEntity.setPrice(keyBoardDto.getPrice());
        productEntity.setDiscount(keyBoardDto.getDiscount());
        BrandEntity brandEntity = brandRepository.findByName(keyBoardDto.getBrand());
        productEntity.setBrand(brandEntity);
        keyBoardEntity.setType(keyBoardDto.getType());
        keyBoardEntity.setLed(keyBoardDto.getLed());
        productEntity.setWeight(keyBoardDto.getWeight());
        productEntity.setInsurance(keyBoardDto.getInsurance());

        productEntity.setCreateDate(java.time.LocalDate.now());

        productEntity.setQuantity(keyBoardDto.getQuantity());
        productEntity.setIsDeleted(false);
        keyBoardEntity.setIsDeleted(false);

        productEntity.setThumbnail(img.getOriginalFilename());
        saveFile(productEntity.getThumbnail(), img);

        // MultipartFile multipartFile = KeyBoardDto.getThumbnail();
        // String fileName = multipartFile.getOriginalFilename();
        // try {
        // FileCopyUtils.copy(KeyBoardDto.getThumbnail().getBytes(), new
        // File(this.fileUpload + fileName));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // productEntity.setThumbnail(fileName);

        keyBoardEntity.setProduct(productEntity);
        keyBoardRepository.save(keyBoardEntity);
        return keyBoardEntity;
    }

    @Override
    public KeyBoardDto editKeyBoard(Integer id) {
        KeyBoardEntity keyBoardEntity = keyBoardRepository.findById(id).get();
        KeyBoardDto keyBoardDto = toDto(keyBoardEntity);
        return keyBoardDto;
    }

    @Override
    public Page<KeyBoardDto> findKeyBoardPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<KeyBoardDto> keyboardDtos = new ArrayList<>();
        List<KeyBoardDto> list = new ArrayList<>();

        for (KeyBoardEntity keyboardEntity : keyBoardRepository.findByIsDeletedIsFalseOrderByIdAsc()) {
            if (keyboardEntity != null)
                keyboardDtos.add(toDto(keyboardEntity));
        }

        if (keyboardDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, keyboardDtos.size());
            list = keyboardDtos.subList(startItem, toIndex);
        }

        Page<KeyBoardDto> keyboardPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), keyboardDtos.size());
        return keyboardPage;
    }

}
