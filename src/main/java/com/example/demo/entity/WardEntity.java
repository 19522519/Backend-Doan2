package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "ward")
public class WardEntity {
    @Id
    private String id;

    private String type;
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private DistrictEntity district;
}
