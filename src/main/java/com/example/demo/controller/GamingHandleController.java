package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class GamingHandleController {
    @GetMapping("/menu_add/add_gaming_handle")
    public String addGamingHandle(Model model) {
        // ScreenDto pcDto = new ScreenDto();
        // model.addAttribute("pc", pcDto);
        return "seller/product/add/add_console";
    }
}

