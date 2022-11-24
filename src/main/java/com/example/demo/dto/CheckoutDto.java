package com.example.demo.dto;

import java.time.LocalDate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckoutDto {
    private Integer userId;
    private Integer orderId;
    private String receiverName;
    private String receiverPhone;
    private String email;
    private String street;
    private String paymentMethod;
    private LocalDate shippingDate;
}
