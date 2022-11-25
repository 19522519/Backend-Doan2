package com.example.demo.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class MenuController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "seller/Dashboard";
    }

    @GetMapping("/menu_add")
    public String menuAdd() {
        return "seller/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct() {
        return "seller/product/menu_product";
    }

    @GetMapping("/order")
    public String orderPage() {
        return "seller/order/order_list";
    }

    @GetMapping("/customer")
    public String customerPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_SELLER")) {
                return "redirect:/403";
            }
        }
        return "seller/customer/customer";
    }

    @GetMapping("/statistic")
    public String statisticPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_SELLER")) {
                return "redirect:/403";
            }
        }
        return "seller/statistic/Statistic";
    }

    @GetMapping("/setting")
    public String settingPage() {
        return "seller/Setting";
    }
}
