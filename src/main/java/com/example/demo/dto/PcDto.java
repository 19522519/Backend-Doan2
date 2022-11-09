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
public class PcDto {
    private Integer PCId;
    private String PCname;
    private String description;
    private String price;
    private String discount;
    private String cpu;
    private String graphicsCard;
    private String ram;
    private String ssd;
    private String mainboard;
    private String Case;
    private String cooler;
    private String insurance;
    private LocalDate createDate;
    private String thumbnail;
    private Integer quantity;
    private Integer inventory;
    private Boolean isDeleted;
}
