package com.example.demo.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.example.demo.dto.LaptopDto;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.LaptopService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/seller")
public class LaptopController {
    @Autowired
    LaptopService laptopService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    LaptopRepository laptopRepository;

    // Save dto
    @GetMapping("/menu_add/add_laptop")
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
        List<String> brandList = brandService.getAllBrands();
        List<String> categoryList = categoryService.getAllCategories();

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
        model.addAttribute("categoryList", categoryList);

        return "seller/product/add/add_laptop";
    }

    @PostMapping("/menu_add/save-new")
    public String saveNewLaptop(@ModelAttribute("laptop") LaptopDto laptopDto, @RequestParam MultipartFile img,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // if (bindingResult.hasErrors()) {
        // model.addAttribute("laptop", laptopDto);
        // return "/seller/add-laptop";
        // }
        LaptopEntity laptopEntity = laptopService.saveNewLaptop(laptopDto, img);
        // redirectAttributes.addFlashAttribute("success", "Insert new laptop
        // successfully!");
        return "redirect:/seller/menu_add/add_laptop?success";
    }

    // Insert into db
    @PostMapping("/LaptopsPage/save-exist")
    public String saveLaptop(@ModelAttribute("laptop") LaptopDto laptopDto, @RequestParam MultipartFile img,
            Model model) {
        LaptopEntity laptopEntity = laptopService.saveExistLaptop(laptopDto, img);
        return "redirect:/seller/LaptopsPage";
    }

    // Pagination Section
    @GetMapping("/LaptopsPage")
    public String showLaptop(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {

        // Laptop Pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        Page<LaptopDto> laptopDtos = laptopService
                .findLaptopPaginated(PageRequest.of(currentPage - 1,
                        pageSize));
        model.addAttribute("laptopPage", laptopDtos);

        int totalPages = laptopDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,
                    totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/seller/product/list/list_laptop";
    }

    // List Laptop old version
    // @GetMapping("/LaptopsPage")
    // public String showLaptopList(@ModelAttribute LaptopDto laptopDto, Model
    // model) {
    // model.addAttribute("laptops", laptopService.findAllLaptop());
    // return "/seller/product/list/list_laptop";
    // }

    // // Phân trang
    // @GetMapping("/list")
    // public String laptopPage(Model model, HttpServletRequest request) {
    // request.getSession().setAttribute("laptopList", null);
    // return "redirect:/seller/laptop/page/1";
    // }

    // @GetMapping("/page/{pageNumber}")
    // public String showLaptopPage(HttpServletRequest request,
    // @PathVariable("pageNumber") Integer pageNumber,
    // Model model) {
    // PagedListHolder<?> pages = (PagedListHolder<?>)
    // request.getSession().getAttribute("laptopList");
    // int pagesize = 3;
    // List<LaptopDto> laptopDtos = new ArrayList<>();
    // List<LaptopEntity> list = (List<LaptopEntity>) laptopRepository.findAll();
    // for (LaptopEntity laptopEntity : list)
    // laptopDtos.add(laptopService.toDto(laptopEntity));
    // System.out.println(list.size());
    // if (pages == null) {
    // pages = new PagedListHolder<>(laptopDtos);
    // pages.setPageSize(pagesize);
    // } else {
    // final int goToPage = pageNumber - 1;
    // if (goToPage <= pages.getPageCount() && goToPage >= 0)
    // pages.setPage(goToPage);
    // }

    // request.getSession().setAttribute("laptopList", pages);
    // int current = pages.getPage() + 1;
    // int begin = Math.max(1, current - laptopDtos.size());
    // int end = Math.min(begin + 5, pages.getPageCount());
    // int totalPageCount = pages.getPageCount();
    // String baseUrl = "/seller/laptop/page/";

    // model.addAttribute("beginIndex", begin);
    // model.addAttribute("endIndex", end);
    // model.addAttribute("currentIndex", current);
    // model.addAttribute("totalPageCount", totalPageCount);
    // model.addAttribute("baseUrl", baseUrl);
    // model.addAttribute("laptops", pages);

    // return "seller/LaptopsPage";
    // }

    @GetMapping("LaptopsPage/delete/{id}")
    public String deleteLaptop(@PathVariable("id") Integer id, Model model) {
        laptopService.deleteLaptop(id);
        return "redirect:/seller/LaptopsPage"; // Đường dẫn get all laptops
    }

    @GetMapping("LaptopsPage/edit/{id}")
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
        List<String> brandList = brandService.getAllBrands();
        List<String> categoryList = categoryService.getAllCategories();

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
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("laptop", laptopService.editLaptop(id));
        return "seller/product/edit/EditLaptopPage";
    }
}
