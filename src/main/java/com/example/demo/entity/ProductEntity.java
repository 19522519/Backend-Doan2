package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    private String name;
    private Double price;
    private String description;
    private byte[] thumbnail;

    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "create_date")
    private LocalDate createDate;

    private String weight;
    private String size;
    private String color;

    private String discount;

    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "insurance_date")
    private LocalDate insuranceDate;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private LaptopEntity laptop;

}
