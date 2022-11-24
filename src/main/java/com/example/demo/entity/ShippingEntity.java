package com.example.demo.entity;

import javax.persistence.*;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
@Table(name = "shipping")
public class ShippingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String receiver;

    private String phone;
    private String email;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
