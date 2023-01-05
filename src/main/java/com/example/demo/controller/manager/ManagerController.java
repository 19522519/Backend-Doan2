package com.example.demo.controller.manager;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderDtoForm;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserOrderDto;
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
@RequestMapping("/manager")
public class ManagerController {
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
    ShippingService shippingService;

    @Autowired
    AddressService addressService;

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

                return "/manager/Dashboard";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/menu_add")
    public String menuAdd() {
        return "/manager/product/menu_add";
    }

    @GetMapping("/menu_list_product")
    public String menuListProduct() {
        return "manager/product/menu_product";
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
                List<String> statusList= new ArrayList<String>();
                statusList.add("Not delivery");
                statusList.add("Delivered");
                
                model.addAttribute("statusList",statusList);
                List<UserOrderDto> userOrderDtos = orderService.showUserOrderPage();
                OrderDtoForm orderDtoForm = new OrderDtoForm();
                orderDtoForm.setUserOrderDtos(userOrderDtos);
                model.addAttribute("orderDtoForm", orderDtoForm);
                return "manager/order/order_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/save-order")
    public String saveOrder(Model model, 
            @ModelAttribute("orderDtoForm") OrderDtoForm orderDtoForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                List<UserOrderDto> userOrderDtos = orderDtoForm.getUserOrderDtos();
                orderService.saveOrder( userOrderDtos);
                if(null != userOrderDtos && userOrderDtos.size() > 0 ){
                    orderService.saveOrder( userOrderDtos);
                }
                

                return "redirect:/manager/order";
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
                // cartItemService.deleteCartItemBasedOnOrder(orderId);
                // paymentService.deletePaymentBasedOnOrder(orderId);
                // addressService.deleteAddressBasedOnShippingBasedOnOrder(orderId);
                // shippingService.deleteShippingBasedOnOrder(orderId);
                orderService.deleteOrder(orderId);

                return "redirect:/manager/order";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/customer-management")
    public String customerPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("customer", userService.findAllCustomer());
                return "manager/user/customer_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/seller-management")
    public String sellerPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("seller", userService.findAllSeller());
                return "manager/user/seller_list";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(Model model, @PathVariable("id") Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                userService.deleteUser(userId);
                return "redirect:/manager/seller-management";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/statistic")
    public String statisticPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("todayRevenue", orderService.getRevenueByToday());
                model.addAttribute("lastMonthRevenue", orderService.getRevenueByLastMonth());
                model.addAttribute("lastWeekRevenue", orderService.getRevenueByLastWeek());

                // Pie Chart
                Map<String, Integer> dataPieChart = new LinkedHashMap<>();
                dataPieChart.put("Laptop", cartItemService.getSumLaptops().size());
                dataPieChart.put("PC", cartItemService.getSumPCs().size());
                dataPieChart.put("Màn hình", cartItemService.getSumScreens().size());
                dataPieChart.put("Chuột", cartItemService.getSumMouses().size());
                dataPieChart.put("Bàn phím", cartItemService.getSumKeyboards().size());

                model.addAttribute("keySet", dataPieChart.keySet());
                model.addAttribute("values", dataPieChart.values());

                return "manager/statistic/Statistic";
            } else {
                return "redirect:/login";
            }
        }
    }

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
                return "manager/Setting";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/personal-info/save")
    public String saveUser(@ModelAttribute("user") UserDto userDto, @RequestParam MultipartFile img, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                model.addAttribute("saveuser","Cập nhật thành công");
                userService.saveUser(appUser, userDto, img);
                return "redirect:/manager/setting";
            } else {
                return "redirect:/login";
            }
        }
    }
}
