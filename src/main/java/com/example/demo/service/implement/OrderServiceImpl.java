package com.example.demo.service.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserOrderDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShippingService;
import com.example.demo.service.ShoppingCartService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AddressService addressService;

    @Autowired
    ShippingService shippingService;

    @Autowired
    PaymentService paymentService;

    @Override
    public OrderEntity createOrderBasedOnUser(AppUser appUser) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setOrderStatus("Not delivery");
        orderEntity.setShippingDate(LocalDate.now().plusDays(3));
        orderEntity.setOrderTotal(null);
        orderEntity.setAppUser(appUser);
        orderEntity.setIsDeleted(false);

        orderRepository.save(orderEntity);

        return orderEntity;
    }

    @Override
    public CheckoutDto showCustomerInfo(Integer orderId) {
        OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(orderId);
        CheckoutDto checkoutDto = new CheckoutDto();

        if (orderEntity != null) {
            checkoutDto.setOrderId(orderId);
            checkoutDto.setReceiverName(
                    orderEntity.getAppUser().getLastName() + " " + orderEntity.getAppUser().getFirstName());
            checkoutDto.setReceiverPhone(orderEntity.getAppUser().getPhone());
            checkoutDto.setEmail(orderEntity.getAppUser().getEmail());
        }

        return checkoutDto;
    }

    @Override
    public void updateUserOrder(AppUser appUser, CheckoutDto checkoutDto) {
        OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(checkoutDto.getOrderId());

        if (orderEntity != null) {
            orderEntity.setOrderTotal(shoppingCartService.calculateTotalMoney(appUser).toString());
            orderRepository.save(orderEntity);

            AddressEntity addressEntity = addressService.createAddressOnAppUser(appUser, checkoutDto);

            if (addressEntity != null)
                shippingService.createShippingBasedOnOrderAndAddress(addressEntity.getId(), checkoutDto);

            paymentService.createPaymentBasedOnOrder(checkoutDto);

            // After user clicks checkout, CartItem is deleted, Order is deleted
            for (CartItemEntity cartItemEntity : orderEntity.getCartItems()) {
                // Update Inventory of Product
                ProductEntity productEntity = cartItemEntity.getProduct();
                productEntity.setInventory(productEntity.getQuantity() - cartItemEntity.getQuantity());
                productRepository.save(productEntity);

                cartItemEntity.setIsDeleted(true);
                cartItemRepository.save(cartItemEntity);
            }
            orderEntity.setIsDeleted(true);
            orderRepository.save(orderEntity);
        }
    }

    @Override
    public List<UserOrderDto> showUserOrderPage(AppUser appUser) {
        List<UserOrderDto> userOrderDtos = new ArrayList<>();

        List<OrderEntity> orderEntities = orderRepository.findByAppUserAndIsDeletedIsFalse(appUser);
        if (orderEntities != null) {
            for (OrderEntity orderEntity : orderEntities) {
                if (orderEntity.getIsDeleted() == true) {
                    List<CartItemEntity> cartItemEntities = orderEntity.getCartItems();
                    if (cartItemEntities != null) {
                        for (CartItemEntity cartItemEntity : cartItemEntities) {
                            if (cartItemEntity.getIsDeleted() == true) {
                                UserOrderDto userOrderDto = new UserOrderDto();
                                userOrderDto.setThumbnail(cartItemEntity.getProduct().getThumbnail());
                                userOrderDto.setProductName(cartItemEntity.getProduct().getName());
                                userOrderDto.setQuantity(cartItemEntity.getQuantity());
                                userOrderDto.setPrice(Integer.parseInt(cartItemEntity.getProduct().getPrice()));
                                userOrderDto.setShippingDate(cartItemEntity.getOrder().getShippingDate());
                                userOrderDto.setStatus(cartItemEntity.getOrder().getOrderStatus());

                                userOrderDtos.add(userOrderDto);
                            }
                        }
                    }
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserOrderDto> showUserOrderPage() {
        List<UserOrderDto> userOrderDtos = new ArrayList<>();

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByIsDeletedIsFalse();
        if (cartItemEntities != null) {
            for (CartItemEntity cartItemEntity : cartItemEntities) {
                if (cartItemEntity.getIsDeleted() == true) {
                    UserOrderDto userOrderDto = new UserOrderDto();
                    userOrderDto.setOrderId(cartItemEntity.getOrder().getId());
                    userOrderDto.setReceiver(cartItemEntity.getOrder().getShipping().getReceiver());
                    userOrderDto.setPhone(cartItemEntity.getOrder().getShipping().getPhone());
                    userOrderDto.setPrice(Integer.parseInt(cartItemEntity.getProduct().getPrice()));
                    userOrderDto.setPaymentMethod(cartItemEntity.getOrder().getPayment().getType());
                    userOrderDto.setShippingDate(cartItemEntity.getOrder().getShippingDate());
                    userOrderDto.setStatus(cartItemEntity.getOrder().getOrderStatus());

                    userOrderDtos.add(userOrderDto);
                }
            }
        }
        return userOrderDtos;
    }
}
