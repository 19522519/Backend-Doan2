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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.KeyBoardDto;
import com.example.demo.entity.KeyBoardEntity;
import com.example.demo.repository.KeyBoardRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.KeyBoardService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class KeyBoardController {
    @Autowired
    KeyBoardService KeyBoardService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    KeyBoardRepository KeyBoardRepository;

    // Save dto
    @GetMapping("/menu_add/add_keyboard")
    public String addKeyBoard(Model model, MultipartFile file) {
        
        List<String> typeList = new ArrayList<>();
        typeList.add("Bàn phím cơ");
        typeList.add("Bàn phím giả cơ");
        typeList.add("Bàn phím văn phòng");


        List<String> brandList = brandService.getAllBrands();
        KeyBoardDto KeyBoardDto = new KeyBoardDto();
        model.addAttribute("keyboard", KeyBoardDto);
        model.addAttribute("typeList", typeList);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_keyboard";
    }

    @PostMapping("/menu_add/save-new-keyboard")
    public String saveNewKeyBoard(@ModelAttribute("keyboard") KeyBoardDto KeyBoardDto,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("KeyBoard", KeyBoardDto);
        // return "/seller/add-KeyBoard";
        // }
        KeyBoardEntity KeyBoardEntity = KeyBoardService.saveNewKeyBoard(KeyBoardDto);
        // redirectAttributes.addFlashAttribute("success", "Insert new KeyBoard
        // successfully!");
        return "redirect:/seller/menu_add/add_keyboard";
    }

    // Insert into db
    @PostMapping("/KeyBoardsPage/save-exist")
    public String saveKeyBoard(@ModelAttribute("keyboard") KeyBoardDto KeyBoardDto, Model model) {
        KeyBoardEntity KeyBoardEntity = KeyBoardService.saveExistKeyBoard(KeyBoardDto);
        return "redirect:/seller/KeyBoardsPage";
    }

    @GetMapping("/KeyBoardsPage")
    public String showKeyBoardList(@ModelAttribute KeyBoardDto KeyBoardDto, Model model) {
        model.addAttribute("keyboards", KeyBoardService.findAllKeyBoard());
        return "/seller/product/list/list_keyboard";
    }

    @GetMapping("KeyBoardsPage/delete/{id}")
    public String deleteKeyBoard(@PathVariable("id") Integer id, Model model) {
        KeyBoardService.deleteKeyBoard(id);
        return "redirect:/seller/KeyBoardsPage"; // Đường dẫn get all KeyBoards
    }

    @GetMapping("KeyBoardsPage/edit/{id}")
    public String editKeyBoard(@PathVariable("id") Integer id, Model model) {
        List<String> typeList = new ArrayList<>();
        typeList.add("Bàn phím cơ");
        typeList.add("Bàn phím giả cơ");
        typeList.add("Bàn phím văn phòng");
        List<String> brandList = brandService.getAllBrands();
       

        KeyBoardDto KeyBoardDto = KeyBoardService.editKeyBoard(id);
        model.addAttribute("keyboard", KeyBoardDto);
        model.addAttribute("typeList", typeList);
        model.addAttribute("brandList", brandList);

        model.addAttribute("keyboard", KeyBoardService.editKeyBoard(id));
        return "seller/product/edit/EditKeyBoardPage";
    }
}



