package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer cartItemId;
    private Integer productId;
    private Integer userId;
    private Integer orderId;
    private String imageUrl;
    private String name;
    private String price;
    private Integer quantity;
    private String productType;
}
