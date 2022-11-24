package com.example.demo.service.implement;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShippingService;
import com.example.demo.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

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
        }
    }
}
