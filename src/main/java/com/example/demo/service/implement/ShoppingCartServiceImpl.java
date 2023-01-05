package com.example.demo.service.implement;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.KeyBoardEntity;
import com.example.demo.entity.LaptopEntity;
import com.example.demo.entity.MouseEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ScreenEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.KeyBoardRepository;
import com.example.demo.repository.LaptopRepository;
import com.example.demo.repository.MouseRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.service.CartItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    KeyBoardRepository keyBoardRepository;

    @Autowired
    MouseRepository mouseRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @Override
    public void createCartItem(AppUser appUser, ProductEntity productEntity, String productType) {
        CartItemEntity cartItemEntity = cartItemRepository.findByProductAndIsDeletedIsFalse(productEntity);

        switch (productType) {
            case "laptop": {
                if (cartItemEntity == null)
                    cartItemService.createCartItem(appUser, productEntity, "laptop");
                else {
                    cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
                    cartItemRepository.save(cartItemEntity);
                }
                break;
            }
            case "screen": {
                if (cartItemEntity == null)
                    cartItemService.createCartItem(appUser, productEntity, "screen");
                else {
                    cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
                    cartItemRepository.save(cartItemEntity);
                }
                break;
            }
            case "keyboard": {
                if (cartItemEntity == null)
                    cartItemService.createCartItem(appUser, productEntity, "keyboard");
                else {
                    cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
                    cartItemRepository.save(cartItemEntity);
                }
                break;
            }
            case "mouse": {
                if (cartItemEntity == null)
                    cartItemService.createCartItem(appUser, productEntity, "mouse");
                else {
                    cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
                    cartItemRepository.save(cartItemEntity);
                }
                break;
            }
        }
    }

    @Override
    public List<CartItemDto> showListCartItems(AppUser appUser) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByAppUserAndIsDeletedIsFalse(appUser);

        if (cartItemEntities.size() != 0) {
            for (CartItemEntity cartItemEntity : cartItemEntities) {
                CartItemDto cartItemDto = new CartItemDto();

                cartItemDto.setCartItemId(cartItemEntity.getId());
                cartItemDto.setImageUrl(cartItemEntity.getProduct().getThumbnail());
                cartItemDto.setName(cartItemEntity.getProduct().getName());
                cartItemDto.setQuantity(cartItemEntity.getQuantity());
                cartItemDto.setPrice(cartItemEntity.getProduct().getPrice());
                cartItemDto.setDiscount(cartItemEntity.getProduct().getDiscount());

                cartItemDto.setProductId(cartItemEntity.getProduct().getId());

                // View detail of product by get id of laptop, screen, keyboard, mouse
                ProductEntity productEntity = cartItemEntity.getProduct();
                switch (cartItemEntity.getProductType()) {
                    case "laptop": {
                        LaptopEntity laptopEntity = laptopRepository.findByProductAndIsDeletedIsFalse(productEntity);
                        cartItemDto.setProductId(laptopEntity.getId());
                        break;
                    }
                    case "screen": {
                        ScreenEntity screenEntity = screenRepository.findByProductAndIsDeletedIsFalse(productEntity);
                        cartItemDto.setProductId(screenEntity.getId());
                        break;
                    }
                    case "keyboard": {
                        KeyBoardEntity keyBoardEntity = keyBoardRepository
                                .findByProductAndIsDeletedIsFalse(productEntity);
                        cartItemDto.setProductId(keyBoardEntity.getId());
                        break;
                    }
                    case "mouse": {
                        MouseEntity mouseEntity = mouseRepository.findByProductAndIsDeletedIsFalse(productEntity);
                        cartItemDto.setProductId(mouseEntity.getId());
                        break;
                    }
                }

                cartItemDto.setProductType(cartItemEntity.getProductType());

                cartItemDtos.add(cartItemDto);
            }
        }
        return cartItemDtos;
    }

    @Override
    public Integer calculateTotalMoney(AppUser appUser) {
        Double totalMoney = 0.0;

      

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByAppUserAndIsDeletedIsFalse(appUser);

        for (CartItemEntity cartItemEntity : cartItemEntities) {
            if (cartItemEntity.getProduct().getDiscount() != null) {
                totalMoney += cartItemEntity.getQuantity()
                        * (Double.parseDouble(cartItemEntity.getProduct().getPrice())
                                * (100 - Double.parseDouble(cartItemEntity.getProduct().getDiscount())) / 100);
            } else {
                totalMoney += cartItemEntity.getQuantity()
                        * Double.parseDouble(cartItemEntity.getProduct().getPrice());
            }

        }
        return totalMoney.intValue();
    }

    @Override
    public Integer countItemInCart(AppUser appUser) {
        Integer countItem = 0;

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByAppUserAndIsDeletedIsFalse(appUser);

        if (cartItemEntities != null) {
            for (CartItemEntity cartItemEntity : cartItemEntities) {
                if (cartItemEntity.getIsDeleted() == false)
                    countItem += cartItemEntity.getQuantity();
            }

        }
        return countItem;
    }

    @Override
    public void removeCartItem(Integer cartItemInteger) {
        CartItemEntity cartItemEntity = cartItemRepository.findByIdAndIsDeletedIsFalse(cartItemInteger);
        if (cartItemEntity != null) {
            cartItemRepository.delete(cartItemEntity);
        }
    }

    @Override
    public void updateQuantityProduct(List<CartItemDto> cartItemDtos) {
        for (CartItemDto cartItemDto : cartItemDtos) {
            CartItemEntity cartItemEntity = cartItemRepository.findByIdAndIsDeletedIsFalse(cartItemDto.getCartItemId());

            cartItemEntity.setQuantity(cartItemDto.getQuantity());
            cartItemRepository.save(cartItemEntity);

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
