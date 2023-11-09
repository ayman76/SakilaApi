package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.City;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.CityRepository;
import com.example.sakilaapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
        City city = cityRepository.findById(addressDto.getCity().getCity_id()).orElseThrow(() -> new RuntimeException("Not Founded City with id: " + addressDto.getCity().getCity_id()));
        address.setCity(city);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public ApiResponse<AddressDto> getAllAddresses(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Address> addresses = addressRepository.findAll(pageable);
        List<Address> listOfAddresses = addresses.getContent();
        List<AddressDto> content = listOfAddresses.stream().map(c -> modelMapper.map(c, AddressDto.class)).toList();

        return getApiResponse(pageNo, pageSize, content, addresses);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address foundedAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + id));
        return modelMapper.map(foundedAddress, AddressDto.class);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Address foundedAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + id));
        City city = cityRepository.findById(addressDto.getCity().getCity_id()).orElseThrow(() -> new RuntimeException("Not Founded City with id: " + addressDto.getCity().getCity_id()));
        foundedAddress.setAddress(addressDto.getAddress());
        foundedAddress.setAddress2(addressDto.getAddress2());
        foundedAddress.setDistrict(addressDto.getDistrict());
        foundedAddress.setCity(city);
        foundedAddress.setPhone(addressDto.getPhone());
        foundedAddress.setPostal_code(addressDto.getPostal_code());
        Address updateAddress = addressRepository.save(foundedAddress);
        return modelMapper.map(updateAddress, AddressDto.class);
    }

    @Override
    public void deleteAddress(Long id) {
        Address foundedAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + id));
        addressRepository.delete(foundedAddress);
    }
}
