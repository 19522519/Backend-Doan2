package com.example.demo.service.implement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {
    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    ProductRepository productRepository;

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
        productEntity.setBrand(laptopDto.getBrand());
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
        laptopDto.setBrand(laptopEntity.getProduct().getBrand());
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

    @Override
    public List<LaptopDto> findAllLaptop() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopRepository.findAll()) {
            if (laptopEntity.getIsDeleted() == false)
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
        productEntity.setBrand(laptopDto.getBrand());
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
    public List<LaptopDto> findAll() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopRepository.findAll(PageRequest.of(0, 5)).getContent()) {
            if (laptopEntity.getIsDeleted() == false)
                laptopDtos.add(toDto(laptopEntity));
        }
        return laptopDtos;
    }
}
