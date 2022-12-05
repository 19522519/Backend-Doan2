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

import com.example.demo.dto.PcDto;
import com.example.demo.entity.PcEntity;
import com.example.demo.repository.PcRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PcService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/manager")
public class PcController {
    @Autowired
    PcService PcService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PcRepository PcRepository;

    // Save dto
    @GetMapping("/menu_add/add_pc")
    public String addPc(Model model, MultipartFile file) {
        // Add list of cpu
        List<String> cpuList = new ArrayList<>();
        cpuList.add("Intel Core I3");
        cpuList.add("Intel Core I5");
        cpuList.add("Intel Core I7");

        // // Add list of ram
        List<String> ramList = new ArrayList<>();
        ramList.add("4GB");
        ramList.add("8GB");
        ramList.add("12GB");
        ramList.add("16GB");

        // // Add list of graphics card
        List<String> graphicsCardList = new ArrayList<>();
        graphicsCardList.add("RTX 2060");
        graphicsCardList.add("RTX 3060");

        // // Add list of SSD
        List<String> ssdList = new ArrayList<>();
        ssdList.add("256GB");
        ssdList.add("512GB");

        // // Add list of OS
        // List<String> operatingSystemList = new ArrayList<>();
        // operatingSystemList.add("Windows 10");
        // operatingSystemList.add("Windows 11");

        // // Add list of battery
        // List<String> batteryList = new ArrayList<>();
        // batteryList.add("4 cells");
        // batteryList.add("6 cells");

        // // Add list of LAN
        // List<String> lanList = new ArrayList<>();
        // lanList.add("Gigabit Ethernet");

        // // Add list of brand
        // List<String> brandList = brandService.getAllBrands();
        // List<String> categoryList = categoryService.getAllCategories();

        PcDto PcDto = new PcDto();
        model.addAttribute("Pc", PcDto);
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("graphicsCardList", graphicsCardList);
        model.addAttribute("ssdList", ssdList);

        return "seller/product/add/add_pc";
    }

    @PostMapping("/menu_add/save-new-pc")
    public String saveNewPc(@ModelAttribute("Pc") PcDto PcDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("Pc", PcDto);
        // return "/seller/add-Pc";
        // }
        PcEntity PcEntity = PcService.saveNewPC(PcDto, img);
        // redirectAttributes.addFlashAttribute("success", "Insert new Pc
        // successfully!");
        return "redirect:/seller/menu_add/add_pc";
    }

    // Insert into db
    @PostMapping("/PcsPage/save-exist")
    public String savePc(@ModelAttribute("Pc") PcDto PcDto, Model model, @RequestParam MultipartFile img) {
        PcEntity PcEntity = PcService.saveExistPc(PcDto, img);
        return "redirect:/seller/PcsPage";
    }

    @GetMapping("/PcsPage")
    public String showPcList(@ModelAttribute PcDto PcDto, Model model) {
        model.addAttribute("Pcs", PcService.findAllPc());
        return "/seller/product/list/list_pc";
    }

    @GetMapping("PcsPage/delete/{id}")
    public String deletePc(@PathVariable("id") Integer id, Model model) {
        PcService.deletePc(id);
        return "redirect:/seller/PcsPage"; // Đường dẫn get all Pcs
    }

    @GetMapping("PcsPage/edit/{id}")
    public String editPc(@PathVariable("id") Integer id, Model model) {
        // Add list of cpu
        // Add list of cpu
        List<String> cpuList = new ArrayList<>();
        cpuList.add("Intel Core I3");
        cpuList.add("Intel Core I5");
        cpuList.add("Intel Core I7");

        // // Add list of ram
        List<String> ramList = new ArrayList<>();
        ramList.add("4GB");
        ramList.add("8GB");
        ramList.add("12GB");
        ramList.add("16GB");

        // // Add list of graphics card
        List<String> graphicsCardList = new ArrayList<>();
        graphicsCardList.add("RTX 2060");
        graphicsCardList.add("RTX 3060");

        // // Add list of SSD
        List<String> ssdList = new ArrayList<>();
        ssdList.add("256GB");
        ssdList.add("512GB");

        PcDto PcDto = PcService.editPc(id);
        model.addAttribute("Pc", PcDto);
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("graphicsCardList", graphicsCardList);
        model.addAttribute("ssdList", ssdList);

        model.addAttribute("Pc", PcService.editPc(id));
        return "seller/product/edit/EditPCPage";
    }
}
