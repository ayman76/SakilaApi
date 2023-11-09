package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {


    private static final Long COUNTRY_ID = 1L;
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
    public void CountryService_GetAllCountries_ReturnCountryDtos() {

        Page<Country> countries = Mockito.mock(Page.class);
        when(countryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(countries);

        ApiResponse<CountryDto> response = countryService.getAllCountries(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void CountryService_GetCountryById_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));

        CountryDto foundedCountry = countryService.getCountryById(COUNTRY_ID);

        Assertions.assertThat(foundedCountry).isNotNull();
        Assertions.assertThat(foundedCountry.getCountry_id()).isEqualTo(COUNTRY_ID);
    }

    @Test
    public void CountryService_UpdateCountry_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));
        when(countryRepository.save(Mockito.any(Country.class))).thenReturn(country);

        CountryDto updatedCountry = countryService.updateCountry(COUNTRY_ID, countryDto);

        Assertions.assertThat(updatedCountry).isNotNull();
        Assertions.assertThat(updatedCountry.getCountry_id()).isEqualTo(COUNTRY_ID);
    }

    @Test
    public void CountryService_DeleteCountry_ReturnCountryDto() {

        when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(country));

        assertAll(() -> countryService.deleteCountry(COUNTRY_ID));
    }

}