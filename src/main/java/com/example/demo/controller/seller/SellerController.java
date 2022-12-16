package com.example.demo.controller.seller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.RevenueService;
import com.example.demo.service.ShippingService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    RevenueService revenueService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    AddressService addressService;

    @Autowired
    ShippingService shippingService;

    @GetMapping({ "/dashboard", "" })
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("countOrders", orderService.countOrders());
                model.addAttribute("countCustomers", userService.countCustomers());
                model.addAttribute("countProducts", productService.countProducts());
                model.addAttribute("revenue", orderService.calculateRevenue());

                model.addAttribute("orders", orderService.displayEightRecentOrders());
                model.addAttribute("customers", userService.displayFiveRecentCustomers());

                // Bar Chart
                Map<String, Integer> dataBarChart = new LinkedHashMap<>();
                dataBarChart.put("Laptop", cartItemService.getSumLaptops().size());
                dataBarChart.put("PC", cartItemService.getSumPCs().size());
                dataBarChart.put("Màn hình", cartItemService.getSumScreens().size());
                dataBarChart.put("Chuột", cartItemService.getSumMouses().size());
                dataBarChart.put("Bàn phím", cartItemService.getSumKeyboards().size());
                // String keySet = "[\"Laptop\", \"PC\", \"Màn hình\", \"Chuột\", \"Bàn
                // phím\"]";

                model.addAttribute("keySet", dataBarChart.keySet());
                model.addAttribute("values", dataBarChart.values());

                // Line Chart
                List<Integer> revenueInt = new ArrayList<>();
                revenueInt.add(revenueService.getRevenueByJan());
                revenueInt.add(revenueService.getRevenueByFeb());
                revenueInt.add(revenueService.getRevenueByMar());
                revenueInt.add(revenueService.getRevenueByApr());
                revenueInt.add(revenueService.getRevenueByMay());
                revenueInt.add(revenueService.getRevenueByJun());
                revenueInt.add(revenueService.getRevenueByJul());
                revenueInt.add(revenueService.getRevenueByAug());
                revenueInt.add(revenueService.getRevenueBySep());
                revenueInt.add(revenueService.getRevenueByOct());
                revenueInt.add(revenueService.getRevenueByNov());
                revenueInt.add(revenueService.getRevenueByDec());

                model.addAttribute("revenueValues", revenueInt);

                return "/seller/Dashboard";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/menu_add")
    public String menuAdd() {
        return "/seller/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct() {
        return "seller/product/menu_product";
    }

    @GetMapping("/order")
    public String orderPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("userOrders", orderService.showUserOrderPage());
                return "seller/order/order_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/customer")
    public String customerPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("customer", userService.findAllCustomer());
                return "/seller/customer_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/delete-order/{id}")
    public String deleteOrder(@PathVariable("id") Integer orderId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                cartItemService.deleteCartItemBasedOnOrder(orderId);
                paymentService.deletePaymentBasedOnOrder(orderId);
                addressService.deleteAddressBasedOnShippingBasedOnOrder(orderId);
                shippingService.deleteShippingBasedOnOrder(orderId);
                orderService.deleteOrder(orderId);

                return "redirect:/seller/order";
            } else {
                return "redirect:/login";
            }
        }
    }

    // @GetMapping("/customer")
    // public String customerPage() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // Collection<? extends GrantedAuthority> authorities =
    // authentication.getAuthorities();
    // for (final GrantedAuthority grantedAuthority : authorities) {
    // String authorityName = grantedAuthority.getAuthority();
    // if (authorityName.equals("ROLE_SELLER")) {
    // return "redirect:/403";
    // }
    // }
    // return "seller/customer/customer";
    // }

    // @GetMapping("/statistic")
    // public String statisticPage() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // Collection<? extends GrantedAuthority> authorities =
    // authentication.getAuthorities();
    // for (final GrantedAuthority grantedAuthority : authorities) {
    // String authorityName = grantedAuthority.getAuthority();
    // if (authorityName.equals("ROLE_SELLER")) {
    // return "redirect:/403";
    // }
    // }
    // return "seller/statistic/Statistic";
    // }

    @GetMapping("/setting")
    public String settingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                UserDto userDto = userService.showUserInfo(appUser);
                model.addAttribute("user", userDto);
                return "seller/Setting";
            } else {
                return "redirect:/login";
            }
        }
    }
}
