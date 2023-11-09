package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CountryDto;

public interface CountryService {
    CountryDto createCountry(CountryDto countryDto);

    ApiResponse<CountryDto> getAllCountries(int pageNo, int pageSize);

    CountryDto getCountryById(Long id);

    CountryDto updateCountry(Long id, CountryDto countryDto);

    void deleteCountry(Long id);
}
