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

import com.example.demo.dto.GamingChairDto;
import com.example.demo.entity.GamingChairEntity;
import com.example.demo.repository.GamingChairRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GamingChairService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class GamingChairController {
    @Autowired
    GamingChairService GamingChairService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GamingChairRepository GamingChairRepository;

    // Save dto
    @GetMapping("/menu_add/add_chair")
    public String addGamingChair(Model model, MultipartFile file) {
        
        
        List<String> brandList = brandService.getAllBrands();
        GamingChairDto GamingChairDto = new GamingChairDto();
        model.addAttribute("GamingChair", GamingChairDto);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_chair";
    }

    @PostMapping("/menu_add/save-new-chair")
    public String saveNewGamingChair(@ModelAttribute("GamingChair") GamingChairDto GamingChairDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("GamingChair", GamingChairDto);
        // return "/seller/add-GamingChair";
        // }
        GamingChairEntity GamingChairEntity = GamingChairService.saveNewGamingChair(GamingChairDto,img);
        // redirectAttributes.addFlashAttribute("success", "Insert new GamingChair
        // successfully!");
        return "redirect:/seller/menu_add/add_chair";
    }

    // Insert into db
    @PostMapping("/GamingChairsPage/save-exist")
    public String saveGamingChair(@ModelAttribute("GamingChair") GamingChairDto GamingChairDto, Model model, @RequestParam MultipartFile img) {
        GamingChairEntity GamingChairEntity = GamingChairService.saveExistGamingChair(GamingChairDto,img);
        return "redirect:/seller/GamingChairsPage";
    }

    @GetMapping("/GamingChairsPage")
    public String showGamingChairList(@ModelAttribute GamingChairDto GamingChairDto, Model model) {
        model.addAttribute("GamingChairs", GamingChairService.findAllGamingChair());
        return "/seller/product/list/list_chair";
    }

    @GetMapping("GamingChairsPage/delete/{id}")
    public String deleteGamingChair(@PathVariable("id") Integer id, Model model) {
        GamingChairService.deleteGamingChair(id);
        return "redirect:/seller/GamingChairsPage"; // Đường dẫn get all GamingChairs
    }

    @GetMapping("GamingChairsPage/edit/{id}")
    public String editGamingChair(@PathVariable("id") Integer id, Model model) {
        List<String> brandList = brandService.getAllBrands();
       

        GamingChairDto GamingChairDto = GamingChairService.editGamingChair(id);
        model.addAttribute("GamingChair", GamingChairDto);
        model.addAttribute("brandList", brandList);

        model.addAttribute("GamingChair", GamingChairService.editGamingChair(id));
        return "seller/product/edit/EditChairPage";
    }
}








