package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ShippingEntity;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingRepository;
import com.example.demo.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ShippingRepository shippingRepository;

    @Override
    public void createShippingBasedOnOrderAndAddress(AddressEntity addressEntity, OrderEntity orderEntity,
            CheckoutDto checkoutDto) {
        ShippingEntity shippingEntity = new ShippingEntity();

        shippingEntity.setOrder(orderEntity);
        shippingEntity.setAddress(addressEntity);

        shippingEntity.setReceiver(checkoutDto.getReceiverName());
        shippingEntity.setPhone(checkoutDto.getReceiverPhone());
        shippingEntity.setEmail(checkoutDto.getEmail());
        shippingEntity.setIsDeleted(false);

        shippingRepository.save(shippingEntity);
    }
}
