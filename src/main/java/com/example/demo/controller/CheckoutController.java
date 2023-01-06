package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.dto.CartItemDto;
import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PaymentEntity;
import com.example.demo.entity.ShippingEntity;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShippingService;
import com.example.demo.service.ShoppingCartService;

@Controller
public class CheckoutController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AddressService addressService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ShippingService shippingService;

    @GetMapping("/shopping-cart/checkout")
    public String showCheckoutPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                // Show Cart Item on Cart Page
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    // Show Customer Info then show on Checkout Page
                    model.addAttribute("customerInfo", orderService.showCustomerInfo(appUser));
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    List<String> paymentMethod = new ArrayList<>();
                    paymentMethod.add("COD");
                    paymentMethod.add("Thẻ ngân hàng");
                    paymentMethod.add("Ví điện tử");
                    model.addAttribute("paymentMethod", paymentMethod);

                    return "CheckoutsPage";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/checkout")
    public String nonOrder() {
        return "redirect:/cart";
    }


    @GetMapping("/checkout-success")
    public String checkoutSuccess() {
        return "CheckoutsSuccess";
    }
    @PostMapping("/order/purchase")
    public String OrderApprovedPage(@ModelAttribute("customerInfo") CheckoutDto checkoutDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                AddressEntity addressEntity = addressService.createAddressOnAppUser(appUser, checkoutDto);
                OrderEntity orderEntity = orderService.createOrderBasedOnUser(appUser);
                paymentService.createPaymentBasedOnOrder(orderEntity, checkoutDto);
                shippingService.createShippingBasedOnOrderAndAddress(addressEntity, orderEntity, checkoutDto);

                return "redirect:/checkout-success";

            } else {
                return "redirect:/login";
            }
        }
    }
}