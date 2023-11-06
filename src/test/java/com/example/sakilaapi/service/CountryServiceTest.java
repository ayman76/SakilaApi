package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.model.Country;
import com.example.sakilaapi.repository.CountryRepository;
import com.example.sakilaapi.service.impl.CountryServiceImpl;
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
class CountryServiceTest {


    private static final Long ACTOR_ID = 1L;
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country country;
    private CountryDto countryDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        countryService = new CountryServiceImpl(modelMapper, countryRepository);
        country = Country.builder().country_id(1L).country("country").build();
        countryDto = CountryDto.builder().country_id(1L).country("country").build();
    }

    @Test
    public void CountryService_CreateCountry_ReturnCountryDto() {

        when(countryRepository.save(Mockito.any(Country.class))).thenReturn(country);

        CountryDto savedCountry = countryService.createCountry(countryDto);

        Assertions.assertThat(savedCountry).isNotNull();
    }

    @Test
    public void CountryService_GetAllCountrys_ReturnCountryDtos() {

        when(countryRepository.findAll()).thenReturn(Arrays.asList(country, country));

        List<CountryDto> countrys = countryService.getAllCountries();

        Assertions.assertThat(countrys).isNotEmpty();
    }

    @Test
    public void CountryService_GetCountryById_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));

        CountryDto foundedCountry = countryService.getCountryById(ACTOR_ID);

        Assertions.assertThat(foundedCountry).isNotNull();
        Assertions.assertThat(foundedCountry.getCountry_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void CountryService_UpdateCountry_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));
        when(countryRepository.save(Mockito.any(Country.class))).thenReturn(country);

        CountryDto updatedCountry = countryService.updateCountry(ACTOR_ID, countryDto);

        Assertions.assertThat(updatedCountry).isNotNull();
        Assertions.assertThat(updatedCountry.getCountry_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void CountryService_DeleteCountry_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));

        assertAll(() -> countryService.deleteCountry(ACTOR_ID));
    }

}