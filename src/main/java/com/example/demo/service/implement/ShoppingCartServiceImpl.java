package com.example.demo.service.implement;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDto;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LaptopRepository laptopRepository;

    Map<Integer, CartItemDto> maps = new HashMap<>();

    @Override
    public void add(CartItemDto item) {
        CartItemDto cartItemDto = maps.get(item.getId());
        if (cartItemDto == null)
            maps.put(item.getId(), item);
        else
            cartItemDto.setQuantity((cartItemDto.getQuantity()) + 1);

    }

    @Override
    public void remove(Integer id) {
        maps.remove(id);
    }

    @Override
    public CartItemDto update(Integer id, Integer quantity) {
        CartItemDto cartItemDto = maps.get(id);
        cartItemDto.setQuantity(quantity);
        return cartItemDto;
    }

    // Remove Cart
    @Override
    public void clear() {
        maps.clear();
    }

    @Override
    public Collection<CartItemDto> getAllItems() {
        return maps.values();
    }

    @Override
    public Integer getCount() {
        return maps.values().size();
    }

    @Override
    public Double getTotalPrice() {
        return maps.values().stream().mapToDouble(item -> item.getQuantity() * Double.parseDouble(item.getPrice()))
                .sum();
    }

    @Override
    public void addToCart(Integer id) {
        LaptopEntity laptopEntity = laptopRepository.findByIdAndIsDeletedIsFalse(id);
        if (laptopEntity != null) {
            ProductEntity productEntity = laptopEntity.getProduct();
            if (productEntity != null) {
                CartItemDto item = new CartItemDto();
                item.setImageUrl(productEntity.getThumbnail());
                item.setName(productEntity.getName());
                item.setPrice(productEntity.getPrice());
                item.setQuantity(1);
                this.add(item);
            }
        }
    }
}
