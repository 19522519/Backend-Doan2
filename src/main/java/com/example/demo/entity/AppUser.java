package com.example.demo.entity;

import java.util.*;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "App_User")
// uniqueConstraints = { //
// @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private Integer enabled;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String avatar;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "appUser")
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "appUser")
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "appUser")
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "appUser")
    private List<CartItemEntity> cartItems;
}
