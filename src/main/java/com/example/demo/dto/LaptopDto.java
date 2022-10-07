package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaptopDto {
    private Integer laptopId;
    private String laptopName;
    private String description;
    private Double price;
    private String discount;
    private String cpu;
    private String graphicsCard;
    private String ram;
    private String storageDrive;
    private String screen;
    private String operatingSystem;
    private String communicationPort;
    private String keyboard;
    private String battery;
    private String size;
    private String weight;
    private String lan;
    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate insuranceDate;
    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate;
    private MultipartFile thumbnail;

}
