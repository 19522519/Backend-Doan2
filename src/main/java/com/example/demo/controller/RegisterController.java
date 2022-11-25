package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registerPage";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult bindingResult, Model model) {
        AppUser existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        // Check existing username
        Boolean isExistUsername = false;
        String username = userDto.getUserName();
        List<AppUser> appUsers = appUserRepository.findAll();
        for (AppUser appUser : appUsers) {
            if (username.equals(appUser.getUserName()))
                isExistUsername = true;
        }
        if (isExistUsername == true)
            model.addAttribute("existUsername", "Username đã tồn tại!");

        // Check valid email address
        String email = userDto.getEmail();
        if (!EmailValidator.getInstance().isValid(email)) {
            model.addAttribute("invalidEmail", "Email không hợp lệ!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        existingUser = new AppUser();
        existingUser = userService.toUserEntity(userDto);
        // Create user role entity
        userRoleService.createUserRole(existingUser);

        return "redirect:/register?success";
    }
}
