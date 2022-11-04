package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class MenuController {
    @GetMapping("/menu_add")
    public String menuAdd(){
        return "seller/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct(){
        return "seller/product/menu_product";
    }

    @GetMapping("/order")
    public String orderPage(){
        return "seller/order/order_list";
    }

    @GetMapping("/customer")
    public String customerPage(){
        return "seller/customer/customer";
    }

    @GetMapping("/statistic")
    public String statisticPage(){
        return "seller/statistic/statistic";
    }

    @GetMapping("/setting")
    public String settingPage(){
        return "seller/setting";
    }
}
