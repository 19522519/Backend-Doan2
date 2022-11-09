package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.ShoppingCartService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/views")
    public String shoppingCart(Model model) {
        model.addAttribute("cart_items", shoppingCartService.getAllItems());
        return "CartPage";
    }

    @GetMapping("/add/{id}")
    public String addCart(@PathVariable("id") Integer id) {
        shoppingCartService.addToCart(id);
        return "redirect:/shopping-cart/views";
    }
}
