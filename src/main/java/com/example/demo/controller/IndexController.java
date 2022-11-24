package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.LaptopDto;
import com.example.demo.service.LaptopService;

@Controller
public class IndexController {
    @Autowired
    LaptopService laptopService;

    @GetMapping({ "/", "/index" })
    public String homePage(Model model) {

        // Laptop Gaming
        List<LaptopDto> laptopDtosGaming = laptopService.findAllLaptopGaming();
        int size = laptopDtosGaming.size();
        List<LaptopDto> first = new ArrayList<>();
        List<LaptopDto> second = new ArrayList<>();
        for (int i = 0; i < size / 2; i++)
            first.add(laptopDtosGaming.get(i));
        for (int i = size / 2; i < size; i++)
            second.add(laptopDtosGaming.get(i));

        // Laptop Van Phong
        List<LaptopDto> laptopDtosVanPhong = laptopService.findAllLaptopVanPhong();

        model.addAttribute("first", first);
        model.addAttribute("second", second);
        model.addAttribute("laptopDtosVanPhong", laptopDtosVanPhong);

        return "index";
    }

    @RequestMapping(value = "/guidepayment", method = RequestMethod.GET)
    public String guidePaymentPage() {
        return "GuidePayMentPage";
    }

    @RequestMapping(value = "/installment", method = RequestMethod.GET)
    public String installmentPage() {
        return "InstallmentPage";
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public String deliveryPage() {
        return "DeliveryPage";
    }
    // @GetMapping("/")
    // public String index(Model model, HttpServletRequest request) {
    // request.getSession().setAttribute("laptopGaminglist", null);
    // return "redirect:/laptop-gaming/page/1/pageSize/5";
    // }

    // // Techmaster
    // @GetMapping({ "/", " " })
    // public String showLaptopGaming(
    // @RequestParam("page") Optional<Integer> page,
    // @RequestParam("size") Optional<Integer> size,
    // Model model) {
    // Page<LaptopDto> laptopDtos = laptopService.getLaptopGamingOnPage(page, size);
    // model.addAttribute("laptopGamingPage", laptopDtos);

    // int totalPages = laptopDtos.getTotalPages();
    // if (totalPages > 0) {
    // List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
    // .boxed()
    // .collect(Collectors.toList());
    // model.addAttribute("pageNumbers", pageNumbers);
    // }

    // return "index";
    // }

    // // Pagination Section
    // @GetMapping({ "/", " " })
    // public String showLaptop(
    // @RequestParam("page") Optional<Integer> page,
    // @RequestParam("size") Optional<Integer> size,
    // Model model) {

    // // Laptop _Gaming Pagination
    // int currentPage_Gaming = page.orElse(1);
    // int pageSize_Gaming = size.orElse(5);

    // Page<LaptopDto> laptopDtos_Gaming = laptopService
    // .findLaptopGamingPaginated(PageRequest.of(currentPage_Gaming - 1,
    // pageSize_Gaming));
    // model.addAttribute("laptopGamingPage", laptopDtos_Gaming);

    // int totalPages_Gaming = laptopDtos_Gaming.getTotalPages();
    // if (totalPages_Gaming > 0) {
    // List<Integer> pageNumbers_Gaming = IntStream.rangeClosed(1,
    // totalPages_Gaming)
    // .boxed()
    // .collect(Collectors.toList());
    // model.addAttribute("pageNumbers_Gaming", pageNumbers_Gaming);
    // }

    // // Laptop _Office Pagination
    // int currentPage_Office = page.orElse(1);
    // int pageSize_Office = size.orElse(5);

    // Page<LaptopDto> laptopDtos_Office = laptopService
    // .findLaptopOfficePaginated(PageRequest.of(currentPage_Office - 1,
    // pageSize_Office));
    // model.addAttribute("laptopOfficePage", laptopDtos_Office);

    // int totalPages_Office = laptopDtos_Office.getTotalPages();
    // if (totalPages_Office > 0) {
    // List<Integer> pageNumbers_Office = IntStream.rangeClosed(1,
    // totalPages_Office)
    // .boxed()
    // .collect(Collectors.toList());
    // model.addAttribute("pageNumbers_Office", pageNumbers_Office);
    // }

    // return "index";
    // }

    @GetMapping("/detail/{id}")
    public String detailPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("laptop", laptopService.detailLaptop(id));
        return "LaptopDetailPage";
    }

    @GetMapping("/collections/laptop-gaming")
    public String collectionsGamingLaptopPage(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage_Gaming = page.orElse(1);
        int pageSize_Gaming = size.orElse(16);

        Page<LaptopDto> laptopDtos_Gaming = laptopService
                .findLaptopGamingPaginated(PageRequest.of(currentPage_Gaming - 1,
                        pageSize_Gaming));
        model.addAttribute("laptopPage", laptopDtos_Gaming);

        int totalPages_Gaming = laptopDtos_Gaming.getTotalPages();
        if (totalPages_Gaming > 0) {
            List<Integer> pageNumbers_Gaming = IntStream.rangeClosed(1,
                    totalPages_Gaming)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers_Gaming);
        }
        return "CollectionPage/GamingLaptopsList";
    }

    @GetMapping("/collections/laptop-van-phong")
    public String collectionsOfficeLaptopPage(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(16);

        Page<LaptopDto> laptopDtos = laptopService
                .findLaptopOfficePaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("laptopPage", laptopDtos);

        int totalPages = laptopDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "CollectionPage/OfficeLaptopsList";
    }
}
