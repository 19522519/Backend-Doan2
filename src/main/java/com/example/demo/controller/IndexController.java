package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.KeyBoardDto;
import com.example.demo.dto.LaptopDto;
import com.example.demo.dto.MouseDto;
import com.example.demo.dto.ScreenDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.KeyboardService;
import com.example.demo.service.LaptopService;
import com.example.demo.service.MouseService;
import com.example.demo.service.ScreenService;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;

@Controller
public class IndexController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    LaptopService laptopService;

    @Autowired
    ScreenService screenService;

    @Autowired
    KeyboardService keyboardService;

    @Autowired
    MouseService mouseService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

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

        // List Màn hình
        List<ScreenDto> screenDtos = screenService.findAllScreen();

        // List Bàn phím
        List<KeyBoardDto> keyboardDtos = keyboardService.findAllKeyBoard();

        // List Chuột
        List<MouseDto> mouseDtos = mouseService.findAllMouse();

        model.addAttribute("first", first);
        model.addAttribute("second", second);
        model.addAttribute("laptopDtosVanPhong", laptopDtosVanPhong);
        model.addAttribute("screenDtos", screenDtos);
        model.addAttribute("keyboardDtos", keyboardDtos);
        model.addAttribute("mouseDtos", mouseDtos);

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

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

    // @RequestMapping(value = "/warranty", method = RequestMethod.GET)
    // public String warrantyPage() {
    // return "WarrantyPage";
    // }
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

    @GetMapping("/laptop/{id}")
    public String LaptopDetailPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("laptop", laptopService.detailLaptop(id));

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "product_detail/LaptopDetailPage";
    }

    @GetMapping("/keyboard/{id}")
    public String keyboardDetailPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("keyboard", keyboardService.keyboardDetail(id));

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "product_detail/KeyboardDetailPage";
    }

    @GetMapping("/screen/{id}")
    public String screenDetailPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("screen", screenService.screenDetail(id));

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "product_detail/ScreenDetailPage";
    }

    @GetMapping("/mouse/{id}")
    public String mouseDetailPage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("mouse", mouseService.mouseDetail(id));

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "product_detail/MouseDetailPage";
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

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
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

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "CollectionPage/OfficeLaptopsList";
    }

    @GetMapping("/collections/man-hinh")
    public String collectionsScreenPage(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(16);

        Page<ScreenDto> screenDtos = screenService
                .findScreenPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("screenPage", screenDtos);

        int totalPages = screenDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "CollectionPage/ScreenList";
    }

    @GetMapping("/collections/ban-phim")
    public String collectionsKeyBoardPage(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(16);

        Page<KeyBoardDto> keyboardDtos = keyboardService
                .findKeyBoardPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("keyboardPage", keyboardDtos);

        int totalPages = keyboardDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "CollectionPage/KeyboardList";
    }

    @GetMapping("/collections/chuot")
    public String collectionsMousePage(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(16);

        Page<MouseDto> laptopDtos = mouseService
                .findMousePaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("mousePage", laptopDtos);

        int totalPages = laptopDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Count Item in Cart of Current User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countItem", "0");
        } else {
            AppUser appUser = appUserRepository.findByUserNameAndIsDeletedIsFalse(authentication.getName());
            model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
        }

        return "CollectionPage/MouseList";
    }
}
