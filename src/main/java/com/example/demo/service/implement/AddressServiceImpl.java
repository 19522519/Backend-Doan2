package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ShippingEntity;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingRepository;
import com.example.demo.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShippingRepository shippingRepository;

    @Override
    public AddressEntity createAddressOnAppUser(AppUser appUser, CheckoutDto checkoutDto) {
        AddressEntity addressEntity = new AddressEntity();

        if (appUser != null)
            addressEntity.setAppUser(appUser);
        addressEntity.setStreet(checkoutDto.getStreet());
        addressEntity.setCity(checkoutDto.getCity());
        addressEntity.setDistrict(checkoutDto.getDistrict());
        addressEntity.setWard(checkoutDto.getWard());
        addressEntity.setIsDeleted(false);
        addressRepository.save(addressEntity);

        return addressEntity;
    }

    @Override
    public void deleteAddressBasedOnShippingBasedOnOrder(Integer orderId) {
        OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(orderId);
        ShippingEntity shippingEntity = shippingRepository.findByOrder(orderEntity);

        AddressEntity addressEntity = shippingEntity.getAddress();
        addressRepository.delete(addressEntity);
    }
}
