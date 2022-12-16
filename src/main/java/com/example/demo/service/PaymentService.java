package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.OrderEntity;

public interface PaymentService {
    void createPaymentBasedOnOrder(OrderEntity orderEntity, CheckoutDto checkoutDto);

    void deletePaymentBasedOnOrder(Integer orderId);
}
