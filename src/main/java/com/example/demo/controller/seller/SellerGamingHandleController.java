package com.example.demo.controller.seller;

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

import com.example.demo.dto.GamingHandleDto;
import com.example.demo.entity.GamingHandleEntity;
import com.example.demo.repository.GamingHandleRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GamingHandleService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class SellerGamingHandleController {
    @Autowired
    GamingHandleService GamingHandleService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GamingHandleRepository GamingHandleRepository;

    // Save dto
    @GetMapping("/menu_add/add_console")
    public String addGamingHandle(Model model, MultipartFile file) {

        List<String> brandList = brandService.getAllBrands();
        GamingHandleDto GamingHandleDto = new GamingHandleDto();
        model.addAttribute("GamingHandle", GamingHandleDto);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_console";
    }

    @PostMapping("/menu_add/save-new-console")
    public String saveNewGamingHandle(@ModelAttribute("GamingHandle") GamingHandleDto GamingHandleDto,
            @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("GamingHandle", GamingHandleDto);
        // return "/seller/add-GamingHandle";
        // }
        GamingHandleEntity GamingHandleEntity = GamingHandleService.saveNewGamingHandle(GamingHandleDto, img);
        // redirectAttributes.addFlashAttribute("success", "Insert new GamingHandle
        // successfully!");
        return "redirect:/seller/menu_add/add_console";
    }

    // Insert into db
    @PostMapping("/GamingHandlesPage/save-exist")
    public String saveGamingHandle(@ModelAttribute("GamingHandle") GamingHandleDto GamingHandleDto, Model model,
            @RequestParam MultipartFile img) {
        GamingHandleEntity GamingHandleEntity = GamingHandleService.saveExistGamingHandle(GamingHandleDto, img);
        return "redirect:/seller/GamingHandlesPage";
    }

    @GetMapping("/GamingHandlesPage")
    public String showGamingHandleList(@ModelAttribute GamingHandleDto GamingHandleDto, Model model) {
        model.addAttribute("GamingHandles", GamingHandleService.findAllGamingHandle());
        return "/seller/product/list/list_console";
    }

    @GetMapping("GamingHandlesPage/delete/{id}")
    public String deleteGamingHandle(@PathVariable("id") Integer id, Model model) {
        GamingHandleService.deleteGamingHandle(id);
        return "redirect:/seller/GamingHandlesPage"; // Đường dẫn get all GamingHandles
    }

    @GetMapping("GamingHandlesPage/edit/{id}")
    public String editGamingHandle(@PathVariable("id") Integer id, Model model) {
        List<String> brandList = brandService.getAllBrands();

        GamingHandleDto GamingHandleDto = GamingHandleService.editGamingHandle(id);
        model.addAttribute("GamingHandle", GamingHandleDto);
        model.addAttribute("brandList", brandList);

        model.addAttribute("GamingHandle", GamingHandleService.editGamingHandle(id));
        return "seller/product/edit/EditConsolePage";
    }
}
