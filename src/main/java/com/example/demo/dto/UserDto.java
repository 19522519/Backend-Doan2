package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String avatar;
    private String userName;
    private String encrytedPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer enabled;
}
