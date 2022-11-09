package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadPhoneDto {
    private Integer HeadPhoneId;
    private String HeadPhonename;
    private String description;
    private String price;
    private String discount;
    private String brand;
    private String insurance;
    private LocalDate createDate;
    private String thumbnail;
    private Integer quantity;
    private Integer inventory;
    private Boolean isDeleted;
    private String weight;
    private String type;
    private String frequency;
    private String color;

}
