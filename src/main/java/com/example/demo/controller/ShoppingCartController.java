package com.example.demo.controller;

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
import com.example.demo.dto.CartItemDtoList;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/cart")
    public String nullCart() {
        return "NullCartPage";
    }

    @GetMapping("/shopping-cart/add-item/{id}")
    public String createCartItem(@PathVariable("id") Integer productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());

            if (appUser != null) {
                // Create Cart Item then show on Cart Page
                shoppingCartService.createCartItem(appUser, productId);
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    Integer orderId = 0;
                    for (OrderEntity orderEntity : orderRepository.findByAppUserAndIsDeletedIsFalse(appUser)) {
                        if (orderEntity != null)
                            orderId = orderEntity.getId();
                    }
                    model.addAttribute("orderId", orderId);
                    return "CartPage";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/shopping-cart/views")
    public String shoppingCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                // Show Cart Item on Cart Page
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos.size() == 0) {
                    return "redirect:/cart";
                } else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    Integer orderId = 0;
                    for (OrderEntity orderEntity : orderRepository.findByAppUserAndIsDeletedIsFalse(appUser)) {
                        if (orderEntity != null)
                            orderId = orderEntity.getId();
                    }
                    model.addAttribute("orderId", orderId);

                    return "CartPage";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/shopping-cart/remove-item/{id}")
    public String removeCartItem(@PathVariable("id") Integer cartItemId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {

                // Remove Cart Item then show on Cart Page
                shoppingCartService.removeCartItem(appUser, cartItemId);
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    Integer orderId = 0;
                    for (OrderEntity orderEntity : orderRepository.findByAppUserAndIsDeletedIsFalse(appUser)) {
                        if (orderEntity != null)
                            orderId = orderEntity.getId();
                    }
                    model.addAttribute("orderId", orderId);

                    return "CartPage";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/shopping-cart/item/update-quantity")
    public String updateCartItemQuantity(@ModelAttribute("CartItemDtoList") CartItemDtoList cartItemDtos, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {

                // Update Quantity of Cart Item
                List<CartItemDto> cartItems = cartItemDtos.getCartItemDtos();
                shoppingCartService.updateQuantityProduct(cartItems);
                return "redirect:/shopping-cart/views";

            } else {
                return "redirect:/login";
            }
        }
    }

    // @GetMapping("/remove-item/{id}/{username}")
    // public String removeCartItem(@PathVariable("id") Integer productId,
    // @PathVariable("username") String username) {
    // cartService.removeCartItem(productId, username);
    // return "redirect:/";
    // }
}
