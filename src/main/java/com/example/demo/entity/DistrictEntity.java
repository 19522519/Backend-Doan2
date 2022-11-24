package com.example.demo.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "district")
@Data
public class DistrictEntity {
    @Id
    private String id;

    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @OneToMany
    private List<WardEntity> wards;
}
