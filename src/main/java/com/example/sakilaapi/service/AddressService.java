package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.ApiResponse;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);

    ApiResponse<AddressDto> getAllAddresses(int pageNo, int pageSize);

    AddressDto getAddressById(Long id);

    AddressDto updateAddress(Long id, AddressDto addressDto);

    void deleteAddress(Long id);
}
