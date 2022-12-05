package com.example.demo.controller.manager;

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

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @GetMapping({ "/dashboard", "" })
    public String dashboard() {
        return "/manager/Dashboard";
    }

    @GetMapping("/menu_add")
    public String menuAdd() {
        return "/manager/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct() {
        return "manager/product/menu_product";
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
                return "manager/order/order_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/customer")
    public String customerPage() {

        return "manager/user/customer";
    }

    @GetMapping("/statistic")
    public String statisticPage() {

        return "manager/statistic/Statistic";
    }

    @GetMapping("/setting")
    public String settingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                UserDto userDto = userService.showUserInfo(appUser);
                model.addAttribute("user", userDto);
                return "manager/Setting";
            } else {
                return "redirect:/login";
            }
        }
    }
}
