package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.OrderEntity;

public interface ShippingService {
    void createShippingBasedOnOrderAndAddress(AddressEntity addressEntity, OrderEntity orderEntity,
            CheckoutDto checkoutDto);
}
