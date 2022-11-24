package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;

public interface PaymentService {
    void createPaymentBasedOnOrder(CheckoutDto checkoutDto);
}
