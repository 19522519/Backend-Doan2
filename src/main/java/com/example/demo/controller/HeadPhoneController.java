package com.example.demo.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.HeadPhoneDto;
import com.example.demo.entity.HeadPhoneEntity;
import com.example.demo.repository.HeadPhoneRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.HeadPhoneService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class HeadPhoneController {
    @Autowired
    HeadPhoneService HeadPhoneService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HeadPhoneRepository HeadPhoneRepository;

    // Save dto
    @GetMapping("/menu_add/add_headphone")
    public String addHeadPhone(Model model, MultipartFile file) {
        
        List<String> typeList = new ArrayList<>();
        typeList.add("Có dây");
        typeList.add("Không dây");

        List<String> frequencyList = new ArrayList<>();
        frequencyList.add("10Hz - 20000Hz");
        frequencyList.add("20Hz - 40000Hz");

        List<String> brandList = brandService.getAllBrands();
        HeadPhoneDto HeadPhoneDto = new HeadPhoneDto();
        model.addAttribute("headphone", HeadPhoneDto);
        model.addAttribute("typeList", typeList);
        model.addAttribute("frequencyList", frequencyList);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_headphone";
    }

    @PostMapping("/menu_add/save-new-headphone")
    public String saveNewHeadPhone(@ModelAttribute("headphone") HeadPhoneDto HeadPhoneDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("HeadPhone", HeadPhoneDto);
        // return "/seller/add-HeadPhone";
        // }
        HeadPhoneEntity HeadPhoneEntity = HeadPhoneService.saveNewHeadPhone(HeadPhoneDto,img);
        // redirectAttributes.addFlashAttribute("success", "Insert new HeadPhone
        // successfully!");
        return "redirect:/seller/menu_add/add_headphone";
    }

    // Insert into db
    @PostMapping("/HeadPhonesPage/save-exist")
    public String saveHeadPhone(@ModelAttribute("headphone") HeadPhoneDto HeadPhoneDto, Model model, @RequestParam MultipartFile img) {
        HeadPhoneEntity HeadPhoneEntity = HeadPhoneService.saveExistHeadPhone(HeadPhoneDto,img);
        return "redirect:/seller/HeadPhonesPage";
    }

    @GetMapping("/HeadPhonesPage")
    public String showHeadPhoneList(@ModelAttribute HeadPhoneDto HeadPhoneDto, Model model) {
        model.addAttribute("headphones", HeadPhoneService.findAllHeadPhone());
        return "/seller/product/list/list_headphone";
    }

    @GetMapping("HeadPhonesPage/delete/{id}")
    public String deleteHeadPhone(@PathVariable("id") Integer id, Model model) {
        HeadPhoneService.deleteHeadPhone(id);
        return "redirect:/seller/HeadPhonesPage"; // Đường dẫn get all HeadPhones
    }

    @GetMapping("HeadPhonesPage/edit/{id}")
    public String editHeadPhone(@PathVariable("id") Integer id, Model model) {
        List<String> typeList = new ArrayList<>();
        typeList.add("Có dây");
        typeList.add("Không dây");

        List<String> frequencyList = new ArrayList<>();
        frequencyList.add("10Hz - 20000Hz");
        frequencyList.add("20Hz - 40000Hz");
        List<String> brandList = brandService.getAllBrands();
       

        HeadPhoneDto HeadPhoneDto = HeadPhoneService.editHeadPhone(id);
        model.addAttribute("headphone", HeadPhoneDto);
        model.addAttribute("typeList", typeList);
        model.addAttribute("frequencyList", frequencyList);
        model.addAttribute("brandList", brandList);

        model.addAttribute("headphone", HeadPhoneService.editHeadPhone(id));
        return "seller/product/edit/EditHeadPhonePage";
    }
}





