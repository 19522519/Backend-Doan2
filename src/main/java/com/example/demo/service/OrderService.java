package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;

public interface OrderService {
    OrderEntity createOrderBasedOnUser(AppUser appUser);

    CheckoutDto showCustomerInfo(Integer orderId);

    void updateUserOrder(AppUser appUser, CheckoutDto checkoutDto);
}
