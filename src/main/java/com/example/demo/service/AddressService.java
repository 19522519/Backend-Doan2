package com.example.demo.service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.AppUser;

public interface AddressService {
    AddressEntity createAddressOnAppUser(AppUser appUser, CheckoutDto checkoutDto);
}
