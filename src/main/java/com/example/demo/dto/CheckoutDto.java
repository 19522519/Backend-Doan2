package com.example.demo.dto;

import java.time.LocalDate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckoutDto {
    private String receiverName;
    private String receiverPhone;
    private String email;
    private String street;
    private String city;
    private String district;
    private String ward;
    private String paymentMethod;
    private LocalDate shippingDate;
}
