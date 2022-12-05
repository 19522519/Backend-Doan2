package com.example.demo.controller.manager;

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

import com.example.demo.dto.MouseDto;
import com.example.demo.entity.MouseEntity;
import com.example.demo.repository.MouseRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.MouseService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/manager")
public class MouseController {
    @Autowired
    MouseService MouseService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MouseRepository MouseRepository;

    // Save dto
    @GetMapping("/menu_add/add_mouse")
    public String addMouse(Model model, MultipartFile file) {

        List<String> connectionList = new ArrayList<>();
        connectionList.add("Có dây");
        connectionList.add("Không dây");

        List<String> ledList = new ArrayList<>();
        ledList.add("Chiếu sáng RGB 8 vùng");
        ledList.add("Chiếu sáng RGB 16 vùng");

        List<String> brandList = brandService.getAllBrands();
        MouseDto MouseDto = new MouseDto();
        model.addAttribute("mouse", MouseDto);
        model.addAttribute("connectionList", connectionList);
        model.addAttribute("ledList", ledList);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_mouse";
    }

    @PostMapping("/menu_add/save-new-mouse")
    public String saveNewMouse(@ModelAttribute("mouse") MouseDto MouseDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("Mouse", MouseDto);
        // return "/seller/add-Mouse";
        // }
        MouseEntity MouseEntity = MouseService.saveNewMouse(MouseDto, img);
        // redirectAttributes.addFlashAttribute("success", "Insert new Mouse
        // successfully!");
        return "redirect:/seller/menu_add/add_mouse";
    }

    // Insert into db
    @PostMapping("/MousesPage/save-exist")
    public String saveMouse(@ModelAttribute("mouse") MouseDto MouseDto, Model model, @RequestParam MultipartFile img) {
        MouseEntity MouseEntity = MouseService.saveExistMouse(MouseDto, img);
        return "redirect:/seller/MousesPage";
    }

    @GetMapping("/MousesPage")
    public String showMouseList(@ModelAttribute MouseDto MouseDto, Model model) {
        model.addAttribute("mouses", MouseService.findAllMouse());
        return "/seller/product/list/list_Mouse";
    }

    @GetMapping("MousesPage/delete/{id}")
    public String deleteMouse(@PathVariable("id") Integer id, Model model) {
        MouseService.deleteMouse(id);
        return "redirect:/seller/MousesPage"; // Đường dẫn get all Mouses
    }

    @GetMapping("MousesPage/edit/{id}")
    public String editMouse(@PathVariable("id") Integer id, Model model) {
        List<String> connectionList = new ArrayList<>();
        connectionList.add("Có dây");
        connectionList.add("Không dây");

        List<String> ledList = new ArrayList<>();
        ledList.add("Chiếu sáng RGB 8 vùng");
        ledList.add("Chiếu sáng RGB 16 vùng");
        List<String> brandList = brandService.getAllBrands();

        MouseDto MouseDto = MouseService.editMouse(id);
        model.addAttribute("mouse", MouseDto);
        model.addAttribute("connectionList", connectionList);
        model.addAttribute("ledList", ledList);
        model.addAttribute("brandList", brandList);

        model.addAttribute("mouse", MouseService.editMouse(id));
        return "seller/product/edit/EditMousePage";
    }
}
