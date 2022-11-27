package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    private Integer cartItemId;
    private Integer userId;
    private String thumbnail;
    private String productName;
    private Integer quantity;

    private Integer price;
    private LocalDate shippingDate;
    private String status;

    private Integer orderId;
    private String receiver;
    private String phone;
    private String paymentMethod;
}
