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

import com.example.demo.dto.ScreenDto;
import com.example.demo.entity.ScreenEntity;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ScreenService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class SellerScreenController {
    @Autowired
    ScreenService screenService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ScreenRepository screenRepository;

    // Save dto
    @GetMapping("/menu_add/add_screen")
    public String addScreen(Model model, MultipartFile file) {

        List<String> sizeList = new ArrayList<>();
        sizeList.add("24 inch");
        sizeList.add("27 inch");

        List<String> resolutionList = new ArrayList<>();
        resolutionList.add("Full HD(1920x1080)");
        resolutionList.add("2K");
        resolutionList.add("4K");

        List<String> ratioList = new ArrayList<>();
        ratioList.add("16:9");
        ratioList.add("16:10");

        List<String> frequencyList = new ArrayList<>();
        frequencyList.add("60Hz");
        frequencyList.add("75Hz");
        frequencyList.add("144Hz");

        List<String> brandList = brandService.getAllBrands();
        ScreenDto screenDto = new ScreenDto();
        model.addAttribute("screen", screenDto);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("resolutionList", resolutionList);
        model.addAttribute("ratioList", ratioList);
        model.addAttribute("frequencyList", frequencyList);
        model.addAttribute("brandList", brandList);

        return "seller/product/add/add_screen";
    }

    @PostMapping("/menu_add/save-new-screen")
    public String saveNewScreen(@ModelAttribute("screen") ScreenDto screenDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("screen", screenDto);
        // return "/seller/add-screen";
        // }
        ScreenEntity screenEntity = screenService.saveNewScreen(screenDto, img);
        // redirectAttributes.addFlashAttribute("success", "Insert new screen
        // successfully!");
        return "redirect:/seller/menu_add/add_screen";
    }

    // Insert into db
    @PostMapping("/ScreensPage/save-exist")
    public String saveScreen(@ModelAttribute("screen") ScreenDto screenDto, Model model,
            @RequestParam MultipartFile img) {
        ScreenEntity screenEntity = screenService.saveExistScreen(screenDto, img);
        return "redirect:/seller/ScreensPage";
    }

    @GetMapping("/ScreensPage")
    public String showScreenList(@ModelAttribute ScreenDto screenDto, Model model) {
        model.addAttribute("screens", screenService.findAllScreen());
        return "/seller/product/list/list_screen";
    }

    @GetMapping("ScreensPage/delete/{id}")
    public String deleteScreen(@PathVariable("id") Integer id, Model model) {
        screenService.deleteScreen(id);
        return "redirect:/seller/ScreensPage"; // Đường dẫn get all screens
    }

    @GetMapping("ScreensPage/edit/{id}")
    public String editScreen(@PathVariable("id") Integer id, Model model) {
        List<String> sizeList = new ArrayList<>();
        sizeList.add("24 inch");
        sizeList.add("27 inch");

        List<String> resolutionList = new ArrayList<>();
        resolutionList.add("Full HD(1920x1080)");
        resolutionList.add("2K");
        resolutionList.add("4K");

        List<String> ratioList = new ArrayList<>();
        ratioList.add("16:9");
        ratioList.add("16:10");

        List<String> frequencyList = new ArrayList<>();
        frequencyList.add("60Hz");
        frequencyList.add("75Hz");
        frequencyList.add("144Hz");

        List<String> brandList = brandService.getAllBrands();

        ScreenDto screenDto = screenService.editScreen(id);
        model.addAttribute("screen", screenDto);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("resolutionList", resolutionList);
        model.addAttribute("ratioList", ratioList);
        model.addAttribute("frequencyList", frequencyList);
        model.addAttribute("brandList", brandList);

        model.addAttribute("screen", screenService.editScreen(id));
        return "seller/product/edit/EditScreenPage";
    }
}
