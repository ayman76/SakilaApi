package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.CountryRepository;
import com.example.sakilaapi.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;

    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        Country country = modelMapper.map(countryDto, Country.class);
        Country savedCountry = countryRepository.save(country);
        return modelMapper.map(savedCountry, CountryDto.class);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream().map(c -> modelMapper.map(c, CountryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country foundedCountry = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Country with id: " + id));
        return modelMapper.map(foundedCountry, CountryDto.class);
    }

    @Override
    public CountryDto updateCountry(Long id, CountryDto countryDto) {
        Country foundedCountry = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Country with id: " + id));
        foundedCountry.setCountry(countryDto.getCountry());
        Country updateCountry = countryRepository.save(foundedCountry);
        return modelMapper.map(updateCountry, CountryDto.class);
    }

    @Override
    public void deleteCountry(Long id) {
        Country foundedCountry = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Country with id: " + id));
        countryRepository.delete(foundedCountry);
    }
}
