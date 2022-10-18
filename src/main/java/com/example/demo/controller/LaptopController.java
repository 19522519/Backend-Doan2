package com.example.demo.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.service.LaptopService;

@Controller
@RequestMapping("/seller/laptop")
public class LaptopController {
    @Autowired
    LaptopService laptopService;

    // Save dto
    @GetMapping("/add")
    public String addLaptop(Model model, MultipartFile file) {
        // Add list of cpu
        List<String> cpuList = new ArrayList<>();
        cpuList.add("Intel Core I3");
        cpuList.add("Intel Core I5");
        cpuList.add("Intel Core I7");

        // Add list of ram
        List<String> ramList = new ArrayList<>();
        ramList.add("4GB");
        ramList.add("8GB");
        ramList.add("12GB");
        ramList.add("16GB");

        // Add list of graphics card
        List<String> graphicsCardList = new ArrayList<>();
        graphicsCardList.add("RTX 2060");
        graphicsCardList.add("RTX 3060");

        // Add list of SSD
        List<String> storageDriveList = new ArrayList<>();
        storageDriveList.add("256GB");
        storageDriveList.add("512GB");

        // Add list of OS
        List<String> operatingSystemList = new ArrayList<>();
        operatingSystemList.add("Windows 10");
        operatingSystemList.add("Windows 11");

        // Add list of battery
        List<String> batteryList = new ArrayList<>();
        batteryList.add("4 cells");
        batteryList.add("6 cells");

        // Add list of LAN
        List<String> lanList = new ArrayList<>();
        lanList.add("Gigabit Ethernet");

        // Add list of brand
        List<String> brandList = new ArrayList<>();
        brandList.add("Acer");
        brandList.add("Assus");
        brandList.add("MSI");
        brandList.add("Lenovo");
        brandList.add("Dell");
        brandList.add("HP");
        brandList.add("Apple");

        LaptopDto laptopDto = new LaptopDto();
        model.addAttribute("laptop", laptopDto);
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("graphicsCardList", graphicsCardList);
        model.addAttribute("storageDriveList", storageDriveList);
        model.addAttribute("operatingSystemList", operatingSystemList);
        model.addAttribute("batteryList", batteryList);
        model.addAttribute("lanList", lanList);
        model.addAttribute("brandList", brandList);

        return "seller/AddLaptopPage";
    }

    @PostMapping("/save-new")
    public String saveNewLaptop(@ModelAttribute("laptop") LaptopDto laptopDto,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("laptop", laptopDto);
        // return "/seller/add-laptop";
        // }
        LaptopEntity laptopEntity = laptopService.saveNewLaptop(laptopDto);
        // redirectAttributes.addFlashAttribute("success", "Insert new laptop
        // successfully!");
        return "redirect:/seller/laptop/add?success";
    }

    // Insert into db
    @PostMapping("/save-exist")
    public String saveLaptop(@ModelAttribute("laptop") LaptopDto laptopDto, Model model) {
        LaptopEntity laptopEntity = laptopService.saveExistLaptop(laptopDto);
        return "redirect:/seller/laptop/all";
    }

    @GetMapping("/all")
    public String showLaptopList(@ModelAttribute LaptopDto laptopDto, Model model) {
        model.addAttribute("laptops", laptopService.findAllLaptop());
        return "seller/LaptopsPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteLaptop(@PathVariable("id") Integer id, Model model) {
        laptopService.deleteLaptop(id);
        return "redirect:/seller/laptops"; // Đường dẫn get all laptops
    }

    @GetMapping("/edit/{id}")
    public String editLaptop(@PathVariable("id") Integer id, Model model) {
        // Add list of cpu
        List<String> cpuList = new ArrayList<>();
        cpuList.add("Intel Core I3");
        cpuList.add("Intel Core I5");
        cpuList.add("Intel Core I7");

        // Add list of ram
        List<String> ramList = new ArrayList<>();
        ramList.add("4GB");
        ramList.add("8GB");
        ramList.add("12GB");
        ramList.add("16GB");

        // Add list of graphics card
        List<String> graphicsCardList = new ArrayList<>();
        graphicsCardList.add("RTX 2060");
        graphicsCardList.add("RTX 3060");

        // Add list of SSD
        List<String> storageDriveList = new ArrayList<>();
        storageDriveList.add("256GB");
        storageDriveList.add("512GB");

        // Add list of OS
        List<String> operatingSystemList = new ArrayList<>();
        operatingSystemList.add("Windows 10");
        operatingSystemList.add("Windows 11");

        // Add list of battery
        List<String> batteryList = new ArrayList<>();
        batteryList.add("4 cells");
        batteryList.add("6 cells");

        // Add list of LAN
        List<String> lanList = new ArrayList<>();
        lanList.add("Gigabit Ethernet");

        // Add list of brand
        List<String> brandList = new ArrayList<>();
        brandList.add("Acer");
        brandList.add("Assus");
        brandList.add("MSI");
        brandList.add("Lenovo");
        brandList.add("Dell");
        brandList.add("HP");
        brandList.add("Apple");

        LaptopDto laptopDto = laptopService.editLaptop(id);
        model.addAttribute("laptop", laptopDto);
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("graphicsCardList", graphicsCardList);
        model.addAttribute("storageDriveList", storageDriveList);
        model.addAttribute("operatingSystemList", operatingSystemList);
        model.addAttribute("batteryList", batteryList);
        model.addAttribute("lanList", lanList);
        model.addAttribute("brandList", brandList);

        model.addAttribute("laptop", laptopService.editLaptop(id));
        return "seller/EditLaptopPage";
    }
}
