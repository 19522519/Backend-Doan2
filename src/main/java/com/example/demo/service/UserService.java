package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.exception.NotFoundException;

public interface UserService {
    AppUser toUserEntity(UserDto userDto);

    UserDto toUserDto(AppUser appUser);

    AppUser findUserByEmail(String email);

    UserDto showUserInfo(AppUser appUser);

    void saveUser(AppUser appUser, UserDto userDto, MultipartFile img);

    String displayUserName(AppUser appUser);

    void updateResetPasswordToken(String token, String email) throws NotFoundException;

    AppUser getByResetPasswordToken(String token);

    void updatePassword(AppUser appUser, String newPassword);

    List<UserDto> findAllCustomer();

    List<UserDto> findAllSeller();

    List<UserDto> displayFiveRecentCustomers();

    Integer countCustomers();

    void deleteUser(Integer id);
}
