package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;

public interface UserService {
    AppUser toUserEntity(UserDto userDto);

    UserDto toUserDto(AppUser appUser);

    AppUser findUserByEmail(String email);

    UserDto showUserInfo(AppUser appUser);

    void saveUser(AppUser appUser, UserDto userDto, MultipartFile img);

    String displayUserName(AppUser appUser);
}
