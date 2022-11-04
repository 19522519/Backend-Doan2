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
}
