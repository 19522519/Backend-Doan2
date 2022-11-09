package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto {
    private Integer ScreenId;
    private String Screenname;
    private String description;
    private String price;
    private String discount;
    private String size;
    private String resolution;
    private String ratio;
    private String communicationPort;
    private String frequency;
    private String brand;
    private String insurance;
    private LocalDate createDate;
    private String thumbnail;
    private String weight;
    private Integer quantity;
    private Integer inventory;
    private Boolean isDeleted;
}
