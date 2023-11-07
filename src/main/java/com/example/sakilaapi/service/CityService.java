package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.CityDto;

import java.util.List;

public interface CityService {
    CityDto createCity(CityDto cityDto);

    List<CityDto> getAllCities();

    CityDto getCityById(Long id);

    CityDto updateCity(Long id, CityDto cityDto);

    void deleteCity(Long id);
}
