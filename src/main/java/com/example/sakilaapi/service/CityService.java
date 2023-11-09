package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CityDto;

public interface CityService {
    CityDto createCity(CityDto cityDto);

    ApiResponse<CityDto> getAllCities(int pageNo, int pageSize);

    CityDto getCityById(Long id);

    CityDto updateCity(Long id, CityDto cityDto);

    void deleteCity(Long id);
}
