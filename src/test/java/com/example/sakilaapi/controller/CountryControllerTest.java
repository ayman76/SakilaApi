package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    private static final String URL = "/api/v1/countries";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Autowired
    private ObjectMapper objectMapper;
    private CountryDto countryDto;
    private ApiResponse<CountryDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        countryDto = CountryDto.builder().country_id(1L).country("country").build();
        responseDto.setContent(Arrays.asList(countryDto, countryDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void CountryController_CreateCountry_ReturnIsCreated() throws Exception {
        when(countryService.createCountry(Mockito.any(CountryDto.class))).thenReturn(countryDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(countryDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CountryController_GetAllCountrys_ReturnIsOk() throws Exception {
        when(countryService.getAllCountries(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CountryController_GetCountryById_ReturnIsOk() throws Exception {
        when(countryService.getCountryById(Mockito.anyLong())).thenReturn(countryDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.country_id", CoreMatchers.is(countryDto.getCountry_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CountryController_UpdateCountry_ReturnIsOk() throws Exception {
        when(countryService.updateCountry(Mockito.anyLong(), Mockito.any(CountryDto.class))).thenReturn(countryDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(countryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.country_id", CoreMatchers.is(countryDto.getCountry_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(countryDto.getCountry())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CountryController_DeleteCountry_ReturnIsOk() throws Exception {
        doNothing().when(countryService).deleteCountry(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(countryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}