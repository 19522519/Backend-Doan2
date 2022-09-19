package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;

public interface UserService {
    AppUser toUserEntity(UserDto userDto);

    UserDto toUserDto(AppUser appUser);

    AppUser findUserByEmail(String email);
}
