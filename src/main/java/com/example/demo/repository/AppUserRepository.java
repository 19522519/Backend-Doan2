package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserNameAndIsDeletedIsFalse(String userName);

    AppUser findByEmailAndIsDeletedIsFalse(String email);

    AppUser findByUserIdAndIsDeletedIsFalse(Long id);

    AppUser findByResetPasswordToken(String resetPasswordToken);

    List<AppUser> findByIsDeletedIsFalseOrderByFirstNameAsc();
}
