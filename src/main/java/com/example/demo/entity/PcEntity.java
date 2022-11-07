package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "pc")
public class PcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpu;
    private String ram;
    private String mainboard;
    private String cooler;
    
    @Column(name="graphics_card")
    private String graphicsCard;

    private String ssd;

    
    @Column(name="computer_case")
    private String Case;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
