package com.example.demo.service.implement;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.CartItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Override
    public void createCartItem(AppUser appUser, Integer productId) {
        LaptopEntity laptopEntity = laptopRepository.findByIdAndIsDeletedIsFalse(productId);
        ProductEntity productEntity = laptopEntity.getProduct();

        // Check cart item trùng thì quantity + 1
        CartItemEntity cartItem = cartItemRepository.findByProductAndAppUserAndIsDeletedIsFalse(productEntity, appUser);
        // Check existing order
        OrderEntity existOrder = orderRepository.findByAppUserAndIsDeletedIsFalse(appUser);

        if (existOrder != null && existOrder.getIsDeleted() == false) {
            if (cartItem != null && cartItem.getIsDeleted() == false) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);

                cartItemRepository.save(cartItem);
            } else {
                // Create Cart Item
                cartItemService.createCartItem(appUser, productEntity, existOrder);
            }
        } else {
            // Create Order
            OrderEntity orderEntity = orderService.createOrderBasedOnUser(appUser);
            // Create Cart Item
            cartItemService.createCartItem(appUser, productEntity, orderEntity);
        }
    }

    @Override
    public List<CartItemDto> showListCartItems(AppUser appUser) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();

        List<CartItemEntity> cartItemEntities = appUser.getCartItems();
        if (cartItemEntities != null) {
            for (CartItemEntity cartItemEntity : cartItemEntities) {
                if (cartItemEntity.getIsDeleted() == false) {
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setCartItemId(cartItemEntity.getId());
                    cartItemDto.setOrderId(cartItemEntity.getOrder().getId());
                    cartItemDto.setImageUrl(cartItemEntity.getProduct().getThumbnail());
                    cartItemDto.setName(cartItemEntity.getProduct().getName());
                    cartItemDto.setQuantity(cartItemEntity.getQuantity());
                    cartItemDto.setPrice(cartItemEntity.getProduct().getPrice());

                    cartItemDtos.add(cartItemDto);
                }
            }
        }

        return cartItemDtos;
    }

    @Override
    public Integer calculateTotalMoney(AppUser appUser) {
        Integer totalMoney = 0;
        List<CartItemEntity> cartItemEntities = appUser.getCartItems();
        if (cartItemEntities != null) {
            for (CartItemEntity cartItemEntity : cartItemEntities) {
                if (cartItemEntity.getIsDeleted() == false)
                    totalMoney += cartItemEntity.getQuantity()
                            * Integer.parseInt(cartItemEntity.getProduct().getPrice());
            }
        }

        return totalMoney;
    }

    @Override
    public void removeCartItem(AppUser appUser, Integer cartItemInteger) {
        CartItemEntity cartItemEntity = cartItemRepository.findByIdAndIsDeletedIsFalse(cartItemInteger);
        if (cartItemEntity != null) {
            // cartItemEntity.setIsDeleted(true);
            // cartItemRepository.save(cartItemEntity);
            cartItemRepository.delete(cartItemEntity);
        }
    }

    @Override
    public void updateQuantityProduct(List<CartItemDto> cartItemDtos) {
        for (CartItemDto cartItemDto : cartItemDtos) {
            CartItemEntity cartItemEntity = cartItemRepository.findByIdAndIsDeletedIsFalse(cartItemDto.getCartItemId());
            if (cartItemEntity != null) {
                cartItemEntity.setQuantity(cartItemDto.getQuantity());
                cartItemRepository.save(cartItemEntity);
            }
        }
    }

    // @Override
    // public void add(CartItemDto item) {
    // CartItemDto cartItemDto = maps.get(item.getId());
    // if (cartItemDto == null)
    // maps.put(item.getId(), item);
    // else
    // cartItemDto.setQuantity((cartItemDto.getQuantity()) + 1);

    // }

    // @Override
    // public void remove(Integer id) {
    // maps.remove(id);
    // }

    // @Override
    // public CartItemDto update(Integer id, Integer quantity) {
    // CartItemDto cartItemDto = maps.get(id);
    // cartItemDto.setQuantity(quantity);
    // return cartItemDto;
    // }

    // // Remove Cart
    // @Override
    // public void clear() {
    // maps.clear();
    // }

    // @Override
    // public Collection<CartItemDto> getAllItems() {
    // return maps.values();
    // }

    // @Override
    // public Integer getCount() {
    // return maps.values().size();
    // }

    // @Override
    // public Double getTotalPrice() {
    // return maps.values().stream().mapToDouble(item -> item.getQuantity() *
    // Double.parseDouble(item.getPrice()))
    // .sum();
    // }

    // @Override
    // public void addToCart(Integer id) {
    // LaptopEntity laptopEntity = laptopRepository.findByIdAndIsDeletedIsFalse(id);
    // if (laptopEntity != null) {
    // ProductEntity productEntity = laptopEntity.getProduct();
    // if (productEntity != null) {
    // CartItemDto item = new CartItemDto();
    // item.setImageUrl(productEntity.getThumbnail());
    // item.setName(productEntity.getName());
    // item.setPrice(productEntity.getPrice());
    // item.setQuantity(1);
    // this.add(item);
    // }
    // }
    // }
}
