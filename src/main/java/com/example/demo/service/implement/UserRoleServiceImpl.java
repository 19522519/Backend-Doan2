package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppRole;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void createUserRole(AppUser appUser) {
        AppRole appRole = appRoleRepository.findByRoleName("ROLE_USER");
        UserRole userRole = new UserRole();
        userRole.setAppUser(appUser);
        userRole.setAppRole(appRole);
        userRoleRepository.save(userRole);
    }
}
