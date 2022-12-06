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
import com.example.demo.dto.CartItemDtoForm;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.KeyBoardEntity;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.MouseEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ScreenEntity;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.KeyBoardRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.MouseRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    MouseRepository mouseRepository;

    @Autowired
    KeyBoardRepository keyBoardRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping("/cart")
    public String nullCart() {
        return "NullCartPage";
    }

    // Add Laptop to Shopping Cart
    @GetMapping("/shopping-cart/add-laptop/{id}")
    public String insertLaptopToCart(@PathVariable("id") Integer laptopId, Model model) {
        LaptopEntity laptopEntity = laptopRepository.findByIdAndIsDeletedIsFalse(laptopId);
        ProductEntity productEntity = laptopEntity.getProduct();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());

            if (appUser != null) {
                // Create Cart Item then show on Cart Page
                shoppingCartService.createCartItem(appUser, productEntity, "laptop");
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
                    return "redirect:/shopping-cart/views";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    // Add Screen to Shopping Cart
    @GetMapping("/shopping-cart/add-screen/{id}")
    public String insertScreenToCart(@PathVariable("id") Integer screenId, Model model) {
        ScreenEntity screenEntity = screenRepository.findByIdAndIsDeletedIsFalse(screenId);
        ProductEntity productEntity = screenEntity.getProduct();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());

            if (appUser != null) {
                // Create Cart Item then show on Cart Page
                shoppingCartService.createCartItem(appUser, productEntity, "screen");
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
                    return "redirect:/shopping-cart/views";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    // Add Keyboard to Shopping Cart
    @GetMapping("/shopping-cart/add-keyboard/{id}")
    public String insertKeyboardToCart(@PathVariable("id") Integer keyboardId, Model model) {
        KeyBoardEntity keyBoardEntity = keyBoardRepository.findByIdAndIsDeletedIsFalse(keyboardId);
        ProductEntity productEntity = keyBoardEntity.getProduct();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());

            if (appUser != null) {
                // Create Cart Item then show on Cart Page
                shoppingCartService.createCartItem(appUser, productEntity, "keyboard");
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
                    return "redirect:/shopping-cart/views";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    // Add Mouse to Shopping Cart
    @GetMapping("/shopping-cart/add-mouse/{id}")
    public String insertMouseToCart(@PathVariable("id") Integer mouseId, Model model) {
        MouseEntity mouseEntity = mouseRepository.findByIdAndIsDeletedIsFalse(mouseId);
        ProductEntity productEntity = mouseEntity.getProduct();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());

            if (appUser != null) {
                // Create Cart Item then show on Cart Page
                shoppingCartService.createCartItem(appUser, productEntity, "mouse");
                List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);

                if (cartItemDtos == null)
                    return "redirect:/cart";
                else {
                    model.addAttribute("cartItems", cartItemDtos);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));
                    return "redirect:/shopping-cart/views";
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
                    // model.Attribute(List)
                    CartItemDtoForm cartItemDtoForm = new CartItemDtoForm();
                    cartItemDtoForm.setCartItemDtos(cartItemDtos);

                    model.addAttribute("cartItemDtoForm", cartItemDtoForm);
                    model.addAttribute("totalMoney", shoppingCartService.calculateTotalMoney(appUser));

                    Integer orderId = 0;
                    for (OrderEntity orderEntity : orderRepository.findByAppUserAndIsDeletedIsFalse(appUser)) {
                        if (orderEntity != null)
                            orderId = orderEntity.getId();
                    }
                    model.addAttribute("orderId", orderId);

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));

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
                shoppingCartService.removeCartItem(cartItemId);
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

                    // Count Item in Cart of Current User
                    model.addAttribute("countItem", shoppingCartService.countItemInCart(appUser));

                    return "redirect:/shopping-cart/views";
                }
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/shopping-cart/item/save")
    public String updateCartItemQuantity(@ModelAttribute("cartItemDtoForm") CartItemDtoForm cartItemDtoForm,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            AppUser appUser = appUserRepository
                    .findByUserNameAndIsDeletedIsFalse(authentication.getName());
            if (appUser != null) {
                List<CartItemDto> cartItemDtos = cartItemDtoForm.getCartItemDtos();
                if (null != cartItemDtos && cartItemDtos.size() > 0) {
                    // Update Quantity of Cart Item
                    shoppingCartService.updateQuantityProduct(cartItemDtos);
                }
                return "redirect:/shopping-cart/views";

            } else {
                return "redirect:/login";
            }
        }
    }
}
