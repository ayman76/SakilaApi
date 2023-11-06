package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto createCountry(CountryDto countryDto);

    List<CountryDto> getAllCountries();

    CountryDto getCountryById(Long id);

    CountryDto updateCountry(Long id, CountryDto countryDto);

    void deleteCountry(Long id);
}
