package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;

public interface ShippingService {
    void createShippingBasedOnOrderAndAddress(Integer addressId, CheckoutDto checkoutDto);
}
