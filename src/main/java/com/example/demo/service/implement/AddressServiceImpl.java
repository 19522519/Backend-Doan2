package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressEntity createAddressOnAppUser(AppUser appUser, CheckoutDto checkoutDto) {
        AddressEntity addressEntity = new AddressEntity();

        if (appUser != null)
            appUser.setAddress(addressEntity);
        addressEntity.setStreet(checkoutDto.getStreet());
        addressEntity.setIsDeleted(false);
        addressRepository.save(addressEntity);

        return addressEntity;
    }
}
