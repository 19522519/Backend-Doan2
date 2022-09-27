package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser toUserEntity(UserDto userDto) {
        AppUser appUser = new AppUser();
        appUser.setLastName(userDto.getLastName());
        appUser.setFirstName(userDto.getFirstName());
        appUser.setEmail(userDto.getEmail());
        appUser.setPhone(userDto.getPhone());
        appUser.setUserName(userDto.getUserName());
        appUser.setEncrytedPassword(bCryptPasswordEncoder.encode(userDto.getEncrytedPassword()));
        appUser.setEnabled(1);

        return appUserRepository.save(appUser);
    }

    @Override
    public UserDto toUserDto(AppUser appUser) {
        UserDto userDto = new UserDto();
        userDto.setLastName(appUser.getLastName());
        userDto.setFirstName(appUser.getFirstName());
        userDto.setEmail(appUser.getEmail());
        userDto.setPhone(appUser.getPhone());
        userDto.setUserName(appUser.getUserName());
        userDto.setEncrytedPassword(appUser.getEncrytedPassword());

        return userDto;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

}
