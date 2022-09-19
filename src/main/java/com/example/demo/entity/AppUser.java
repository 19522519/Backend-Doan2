package com.example.demo.entity;

import java.util.List;

import javax.persistence.*;

import org.springframework.web.jsf.FacesContextUtils;

import lombok.Data;

@Entity
@Data
@Table(name = "App_User") //
// uniqueConstraints = { //
// @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false, columnDefinition = "serial")
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    // public Long getUserId() {
    // return userId;
    // }

    // public void setUserId(Long userId) {
    // this.userId = userId;
    // }

    // public String getUserName() {
    // return userName;
    // }

    // public void setUserName(String userName) {
    // this.userName = userName;
    // }

    // public String getEncrytedPassword() {
    // return encrytedPassword;
    // }

    // public void setEncrytedPassword(String encrytedPassword) {
    // this.encrytedPassword = encrytedPassword;
    // }

    // public boolean isEnabled() {
    // return enabled;
    // }

    // public void setEnabled(boolean enabled) {
    // this.enabled = enabled;
    // }
}
