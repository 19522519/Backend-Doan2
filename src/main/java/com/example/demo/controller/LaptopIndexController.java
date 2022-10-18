package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LaptopDto;
import com.example.demo.service.LaptopService;

@Controller
public class LaptopIndexController {
    @Autowired
    LaptopService laptopService;

    @GetMapping({ "/", " " })
    public String homePage(Model model) {
        List<LaptopDto> laptopDtos = laptopService.findAll();
        model.addAttribute("laptops", laptopDtos);
        return "index";
    }

    @GetMapping("/detail")
    public String detailPage() {
        return "LaptopDetail";
    }
}
