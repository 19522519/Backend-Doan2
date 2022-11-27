package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

@Controller
public class CustomerController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/personal-info")
    public String PersonalPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("username", appUser.getUserName());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedString = LocalDate.now().format(formatter);
                model.addAttribute("today", formattedString);
                return "customer/CustomerPage";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/personal-info/account")
    public String PersonalAccountManagement(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                UserDto userDto = userService.showUserInfo(appUser);
                model.addAttribute("user", userDto);
                return "customer/UserAccountInfo";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/personal-info/save-user")
    public String saveUser(@ModelAttribute("user") UserDto userDto, @RequestParam MultipartFile img) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                userService.saveUser(appUser, userDto, img);
                return "redirect:/personal-info/account";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/warranty")
    public String WarrantyPage() {
        return "customer/WarrantyPage";
    }

    @GetMapping("/personal-info/orders")
    public String UserOrderPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("userOrders", orderService.showUserOrderPage(appUser));
                return "customer/UserOrder";
            } else {
                return "redirect:/login";
            }
        }
    }
}
