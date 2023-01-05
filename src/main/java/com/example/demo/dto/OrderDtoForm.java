package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoForm {
    private List<UserOrderDto> userOrderDtos;
    public List<UserOrderDto> getUserOrderDtos() {
        if (this.userOrderDtos == null) {
            this.userOrderDtos = new ArrayList<>();
        }
        return userOrderDtos;
    }

    public void setUserOrderDtos(List<UserOrderDto> userOrderDtos) {
        this.userOrderDtos = userOrderDtos;
    }
}
