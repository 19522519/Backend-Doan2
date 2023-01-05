package com.example.demo.service.implement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppRole;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    CartItemRepository cartItemRepository;

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
        appUser.setIsDeleted(false);
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
        return appUserRepository.findByEmailAndIsDeletedIsFalse(email);
    }

    @Override
    public UserDto showUserInfo(AppUser appUser) {
        UserDto userDto = new UserDto();

        userDto.setUserId(appUser.getUserId());
        userDto.setAvatar(appUser.getAvatar());
        userDto.setLastName(appUser.getLastName());
        userDto.setFirstName(appUser.getFirstName());
        userDto.setEmail(appUser.getEmail());
        userDto.setPhone(appUser.getPhone());
        userDto.setFullName(appUser.getLastName() + " " + appUser.getFirstName());
        List<UserRole> userRoles = userRoleRepository.findByAppUser(appUser);
        List<String> roleNames = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleNames.add(userRole.getAppRole().getRoleName());
        }
        userDto.setRole(roleNames.toString());

        return userDto;
    }

    public void saveFile(String image, MultipartFile img) {
        if (image != null) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(AppUser appUser, UserDto userDto, MultipartFile img) {
        appUser.setFirstName(userDto.getFirstName());
        appUser.setLastName(userDto.getLastName());
        appUser.setEmail(userDto.getEmail());
        appUser.setPhone(userDto.getPhone());
        if(!img.isEmpty()) {

            appUser.setAvatar(img.getOriginalFilename());
            saveFile(appUser.getAvatar(), img);
        }
        
        appUserRepository.save(appUser);
    }

    @Override
    public String displayUserName(AppUser appUser) {
        return appUser.getFirstName();
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        AppUser appUser = appUserRepository.findByEmailAndIsDeletedIsFalse(email);

        if (appUser != null) {
            appUser.setResetPasswordToken(token);
            appUserRepository.save(appUser);
        } else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public AppUser getByResetPasswordToken(String token) {
        return appUserRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(AppUser appUser, String newPassword) {
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        appUser.setEncrytedPassword(encodePassword);
        appUser.setResetPasswordToken(null);
        appUserRepository.save(appUser);
    }

    @Override
    public List<UserDto> findAllCustomer() {
        List<UserDto> userDtos = new ArrayList<>();

        AppRole appRole = appRoleRepository.findByRoleName("ROLE_CUSTOMER");
        List<AppUser> appUsers = appUserRepository.findByIsDeletedIsFalseOrderByFirstNameAsc();

        for (AppUser appUser : appUsers) {
            List<UserRole> userRoles = userRoleRepository.findByAppUser(appUser);

            for (UserRole userRole : userRoles) {
                if (userRole.getAppRole().getRoleId() == appRole.getRoleId()) {
                    UserDto userDto = new UserDto();

                    userDto.setUserId(appUser.getUserId());
                    userDto.setAvatar(appUser.getAvatar());
                    userDto.setLastName(appUser.getLastName());
                    userDto.setFirstName(appUser.getFirstName());
                    userDto.setEmail(appUser.getEmail());
                    userDto.setPhone(appUser.getPhone());

                    userDtos.add(userDto);
                }
            }
        }

        return userDtos;
    }

    @Override
    public List<UserDto> findAllSeller() {
        List<UserDto> userDtos = new ArrayList<>();

        AppRole appRole = appRoleRepository.findByRoleName("ROLE_SELLER");
        List<AppUser> appUsers = appUserRepository.findByIsDeletedIsFalseOrderByFirstNameAsc();

        for (AppUser appUser : appUsers) {
            List<UserRole> userRoles = userRoleRepository.findByAppUser(appUser);

            for (UserRole userRole : userRoles) {
                if (userRole.getAppRole().getRoleId() == appRole.getRoleId()) {
                    UserDto userDto = new UserDto();

                    userDto.setUserId(appUser.getUserId());
                    userDto.setAvatar(appUser.getAvatar());
                    userDto.setLastName(appUser.getLastName());
                    userDto.setFirstName(appUser.getFirstName());
                    userDto.setEmail(appUser.getEmail());
                    userDto.setPhone(appUser.getPhone());

                    userDtos.add(userDto);
                }
            }
        }

        return userDtos;
    }

    @Override
    public List<UserDto> displayFiveRecentCustomers() {
        List<UserDto> userDtos = new ArrayList<>();
        Pageable topFive = PageRequest.of(0, 5);

        AppRole appRole = appRoleRepository.findByRoleName("ROLE_CUSTOMER");
        List<UserRole> userRoles = userRoleRepository.findByAppRoleOrderByIdDesc(appRole, topFive);

        for (UserRole userRole : userRoles) {
            UserDto userDto = new UserDto();

            userDto.setUserId(userRole.getAppUser().getUserId());
            userDto.setAvatar(userRole.getAppUser().getAvatar());
            userDto.setFullName(userRole.getAppUser().getLastName() + " " + userRole.getAppUser().getFirstName());

            userDtos.add(userDto);
        }

        return userDtos;
    }

    @Override
    public Integer countCustomers() {
        AppRole appRole = appRoleRepository.findByRoleName("ROLE_CUSTOMER");
        return userRoleRepository.findByAppRole(appRole).size();
    }

    @Override
    public void deleteUser(Integer id) {
        AppUser appUser = appUserRepository.findByUserIdAndIsDeletedIsFalse(id);

        List<UserRole> userRoles = userRoleRepository.findByAppUser(appUser);
        for (UserRole userRole : userRoles) {
            userRoleRepository.delete(userRole);
        }

        appUserRepository.delete(appUser);
    }
}
