package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppRole;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByAppUser(AppUser appUser);

    List<UserRole> findByAppRoleOrderByIdDesc(AppRole appRole, Pageable pageable);

    List<UserRole> findByAppRole(AppRole appRole);
}
