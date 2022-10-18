package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.PcDto;
import com.example.demo.entity.PcEntity;
import com.example.demo.service.PcService;

@Controller
@RequestMapping("/seller")
public class PcController {
    @Autowired
    PcService pcService;

    @GetMapping("/add-pc")
    public String addPc(Model model) {
        PcDto pcDto = new PcDto();
        model.addAttribute("pc", pcDto);
        return "seller/AddPcPage";
    }

    @PostMapping("/pc/save")
    public String saveLaptop(@ModelAttribute("pc") PcDto laptopDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pc", laptopDto);
            return "/seller/add-pc";
        }

        PcEntity laptopEntity = pcService.toEntity(laptopDto);
        return "redirect:/seller/add-pc?success";
    }

}
