package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.dto.UserOrderDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;

public interface OrderService {
    OrderEntity createOrderBasedOnUser(AppUser appUser);

    CheckoutDto showCustomerInfo(AppUser appUser);

    // Customer Page
    List<UserOrderDto> showUserOrderPage(AppUser appUser);

    // Seller Page
    List<UserOrderDto> showUserOrderPage();
}
