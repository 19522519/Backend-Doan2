package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserNameAndIsDeletedIsFalse(String userName);

    AppUser findByEmailAndIsDeletedIsFalse(String email);

    AppUser findByUserIdAndIsDeletedIsFalse(Integer id);
}
