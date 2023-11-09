package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.CountryRepository;
import com.example.sakilaapi.service.CountryService;
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
    public ApiResponse<CountryDto> getAllCountries(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Country> countries = countryRepository.findAll(pageable);
        List<Country> listOfCountries = countries.getContent();
        List<CountryDto> content = listOfCountries.stream().map(c -> modelMapper.map(c, CountryDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, countries);
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
