package com.example.demo.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "city")
public class CityEntity {
    @Id
    private String id;

    private String name;
    private String type;
    private String slug;

    @OneToMany
    private List<DistrictEntity> districts;
}
