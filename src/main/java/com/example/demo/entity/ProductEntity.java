package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

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
    private String price;
    private String description;

    private String thumbnail;

    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "create_date")
    private LocalDate createDate;

    private String weight;
    private String size;
    private String color;
    private String discount;
    private String insurance;
    private Integer quantity;
    private Integer inventory;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private LaptopEntity laptop;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PcEntity pc;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product")
    private List<ImageEntity> images;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CartItemEntity cartItem;
}
