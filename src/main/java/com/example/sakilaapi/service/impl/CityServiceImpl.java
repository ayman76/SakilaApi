package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.model.City;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.CityRepository;
import com.example.sakilaapi.repository.CountryRepository;
import com.example.sakilaapi.service.CityService;
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
public class CityServiceImpl implements CityService {
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public CityDto createCity(CityDto cityDto) {
        City city = modelMapper.map(cityDto, City.class);
        Country foundedCountry = countryRepository.findById(cityDto.getCountry().getCountry_id()).orElseThrow(() -> new RuntimeException("Not Founded Country with id: " + cityDto.getCountry().getCountry_id()));
        city.setCountry(foundedCountry);
        City savedCity = cityRepository.save(city);
        return modelMapper.map(savedCity, CityDto.class);
    }

    @Override
    public ApiResponse<CityDto> getAllCities(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<City> cities = cityRepository.findAll(pageable);
        List<City> listOfCities = cities.getContent();
        List<CityDto> content = listOfCities.stream().map(c -> modelMapper.map(c, CityDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, cities);
    }

    @Override
    public CityDto getCityById(Long id) {
        City foundedCity = cityRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded City with id: " + id));
        return modelMapper.map(foundedCity, CityDto.class);
    }

    @Override
    public CityDto updateCity(Long id, CityDto cityDto) {
        City foundedCity = cityRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded City with id: " + id));
        Country foundedCountry = countryRepository.findById(cityDto.getCountry().getCountry_id()).orElseThrow(() -> new RuntimeException("Not Founded Country with id: " + cityDto.getCountry().getCountry_id()));
        foundedCity.setCity(cityDto.getCity());
        foundedCity.setCountry(foundedCountry);
        City updateCity = cityRepository.save(foundedCity);
        return modelMapper.map(updateCity, CityDto.class);
    }

    @Override
    public void deleteCity(Long id) {
        City foundedCity = cityRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded City with id: " + id));
        cityRepository.delete(foundedCity);
    }
}
