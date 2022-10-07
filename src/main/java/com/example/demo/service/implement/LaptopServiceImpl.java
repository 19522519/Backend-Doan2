package com.example.demo.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {
    @Autowired
    LaptopRepository laptopRepository;

    @Override
    public LaptopEntity toEntity(LaptopDto laptopDto) {
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
        productEntity.setInsuranceDate(laptopDto.getInsuranceDate());

        productEntity.setCreateDate(java.time.LocalDate.now());

        productEntity.setThumbnail(convertToBytes(laptopDto.getThumbnail()));

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
        laptopDto.setBattery(laptopEntity.getKeyboard());
        laptopDto.setSize(laptopEntity.getProduct().getSize());
        laptopDto.setWeight(laptopEntity.getProduct().getWeight());
        laptopDto.setLan(laptopEntity.getLan());
        laptopDto.setInsuranceDate(laptopEntity.getProduct().getInsuranceDate());
        laptopDto.setCreateDate(laptopEntity.getProduct().getCreateDate());

        return laptopDto;
    }

    byte[] byteObjects;

    private byte[] convertToBytes(MultipartFile file) {
        try {
            byteObjects = new byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
        } catch (Exception ex) {
            System.out.println("Image not upload successfully!");
        }

        return byteObjects;
    }

    @Override
    public List<LaptopDto> findAllLaptop() {
        List<LaptopDto> laptopDtos = new ArrayList<>();
        for (LaptopEntity laptopEntity : laptopRepository.findAll()) {
            laptopDtos.add(toDto(laptopEntity));
        }
        return laptopDtos;
    }
}
