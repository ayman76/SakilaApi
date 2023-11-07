package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.City;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.CityRepository;
import com.example.sakilaapi.service.impl.AddressServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {


    private static final Long ADDRESS_ID = 1L;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDto addressDto;
    private City city;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        addressService = new AddressServiceImpl(modelMapper, addressRepository, cityRepository);

        city = City.builder().city_id(1L).city("city").country(new Country()).build();
        CityDto cityDto = CityDto.builder().city_id(1L).city("city").country(new CountryDto()).build();

        address = Address.builder().address_id(1L).address("address").city(city).build();
        addressDto = AddressDto.builder().address_id(1L).address("address").city(cityDto).build();
    }

    @Test
    public void AddressService_CreateAddress_ReturnAddressDto() {

        when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(city));
        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        AddressDto savedAddress = addressService.createAddress(addressDto);

        Assertions.assertThat(savedAddress).isNotNull();
    }

    @Test
    public void AddressService_GetAllAddresses_ReturnAddressDtos() {

        when(addressRepository.findAll()).thenReturn(Arrays.asList(address, address));

        List<AddressDto> addresses = addressService.getAllAddresses();

        Assertions.assertThat(addresses).isNotEmpty();
    }

    @Test
    public void AddressService_GetAddressById_ReturnAddressDto() {

        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(address));

        AddressDto foundedAddress = addressService.getAddressById(ADDRESS_ID);

        Assertions.assertThat(foundedAddress).isNotNull();
        Assertions.assertThat(foundedAddress.getAddress_id()).isEqualTo(ADDRESS_ID);
    }

    @Test
    public void AddressService_UpdateAddress_ReturnAddressDto() {

        when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(city));
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(address));
        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        AddressDto updatedAddress = addressService.updateAddress(ADDRESS_ID, addressDto);

        Assertions.assertThat(updatedAddress).isNotNull();
        Assertions.assertThat(updatedAddress.getAddress_id()).isEqualTo(ADDRESS_ID);
    }

    @Test
    public void AddressService_DeleteAddress_ReturnAddressDto() {

        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(address));

        assertAll(() -> addressService.deleteAddress(ADDRESS_ID));
    }

}