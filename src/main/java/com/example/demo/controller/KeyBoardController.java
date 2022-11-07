package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class KeyBoardController {
    @GetMapping("/menu_add/add_keyboard")
    public String addKeyBoard(Model model) {
        // ScreenDto pcDto = new ScreenDto();
        // model.addAttribute("pc", pcDto);
        return "seller/product/add/add_keyboard";
    }
}
