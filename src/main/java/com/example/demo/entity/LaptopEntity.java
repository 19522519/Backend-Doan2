package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "laptop")
public class LaptopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpu;
    private String ram;

    @Column(name = "storage_drive")
    private String storageDrive;

    @Column(name = "graphics_card")
    private String graphicsCard;

    private String screen;
    private String keyboard;

    @Column(name = "communication_port")
    private String communicationPort;

    @Column(name = "operating_system")
    private String operatingSystem;

    private String battery;
    private String lan;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
