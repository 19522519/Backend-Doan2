package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "user_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_total")
    private String orderTotal;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ShippingEntity shipping;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}
