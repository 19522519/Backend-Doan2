package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class MouseController {
    @GetMapping("/menu_add/add_mouse")
    public String addMouse(Model model) {
        // ScreenDto pcDto = new ScreenDto();
        // model.addAttribute("pc", pcDto);
        return "seller/product/add/add_mouse";
    }
}
