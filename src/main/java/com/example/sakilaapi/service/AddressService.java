package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);

    List<AddressDto> getAllAddresses();

    AddressDto getAddressById(Long id);

    AddressDto updateAddress(Long id, AddressDto addressDto);

    void deleteAddress(Long id);
}
