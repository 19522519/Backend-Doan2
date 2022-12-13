package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @GetMapping("/seller")
    public String sellerPage() {
        return "seller/dashboard";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {

        return "loginPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "loginPage";
    }

    // @GetMapping("/register")
    // public String signup(@ModelAttribute("user") AppUser user, ModelMap modelMap)
    // {
    // modelMap.addAttribute("user", user);
    // return "registerPage";
    // }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDeniedPage() {
        return "403Page";
    }
}
