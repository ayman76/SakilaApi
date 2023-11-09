package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.model.City;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.CityRepository;
import com.example.sakilaapi.repository.CountryRepository;
import com.example.sakilaapi.service.impl.CityServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {


    private static final Long CITY_ID = 1L;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private City city;
    private CityDto cityDto;
    private Country country;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        cityService = new CityServiceImpl(modelMapper, cityRepository, countryRepository);

        country = Country.builder().country_id(1L).country("country").build();
        CountryDto countryDto = CountryDto.builder().country_id(1L).country("country").build();

        city = City.builder().city_id(1L).city("city").country(country).build();
        cityDto = CityDto.builder().city_id(1L).city("city").country(countryDto).build();
    }

    @Test
    public void CityService_CreateCity_ReturnCityDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));
        when(cityRepository.save(Mockito.any(City.class))).thenReturn(city);

        CityDto savedCity = cityService.createCity(cityDto);

        Assertions.assertThat(savedCity).isNotNull();
    }

    @Test
    public void CityService_GetAllCities_ReturnCityDtos() {
        Page<City> cities = Mockito.mock(Page.class);

        when(cityRepository.findAll(Mockito.any(Pageable.class))).thenReturn(cities);

        ApiResponse<CityDto> response = cityService.getAllCities(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void CityService_GetCityById_ReturnCityDto() {

        when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(city));

        CityDto foundedCity = cityService.getCityById(CITY_ID);

        Assertions.assertThat(foundedCity).isNotNull();
        Assertions.assertThat(foundedCity.getCity_id()).isEqualTo(CITY_ID);
    }

    @Test
    public void CityService_UpdateCity_ReturnCityDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));
        when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(city));
        when(cityRepository.save(Mockito.any(City.class))).thenReturn(city);

        CityDto updatedCity = cityService.updateCity(CITY_ID, cityDto);

        Assertions.assertThat(updatedCity).isNotNull();
        Assertions.assertThat(updatedCity.getCity_id()).isEqualTo(CITY_ID);
    }

    @Test
    public void CityService_DeleteCity_ReturnCityDto() {

        when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(city));

        assertAll(() -> cityService.deleteCity(CITY_ID));
    }

}