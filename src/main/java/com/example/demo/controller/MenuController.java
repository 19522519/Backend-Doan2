package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.OrderService;

@Controller
@RequestMapping("/seller")
public class MenuController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public String dashboard() {
        return "/seller/";
    }

    @GetMapping("/menu_add")
    public String menuAdd() {
        return "/seller/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct() {
        return "seller/product/menu_product";
    }

    @GetMapping("/order")
    public String orderPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("userOrders", orderService.showUserOrderPage());
                return "seller/order/order_list";
            } else {
                return "redirect:/login";
            }
        }
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
