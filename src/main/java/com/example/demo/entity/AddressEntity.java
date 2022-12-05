package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private String ward;
    private String district;
    private String city;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @OneToOne(mappedBy = "address")
    private ShippingEntity shipping;
}
