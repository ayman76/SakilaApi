package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.service.CityService;
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

@WebMvcTest(controllers = CityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    private static final String URL = "/api/v1/cities";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Autowired
    private ObjectMapper objectMapper;
    private CityDto cityDto;
    private ApiResponse<CityDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        cityDto = CityDto.builder().city_id(1L).city("city").build();
        responseDto.setContent(Arrays.asList(cityDto, cityDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void CityController_CreateCity_ReturnIsCreated() throws Exception {
        when(cityService.createCity(Mockito.any(CityDto.class))).thenReturn(cityDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cityDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CityController_GetAllCities_ReturnIsOk() throws Exception {
        when(cityService.getAllCities(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CityController_GetCityById_ReturnIsOk() throws Exception {
        when(cityService.getCityById(Mockito.anyLong())).thenReturn(cityDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city_id", CoreMatchers.is(cityDto.getCity_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CityController_UpdateCity_ReturnIsOk() throws Exception {
        when(cityService.updateCity(Mockito.anyLong(), Mockito.any(CityDto.class))).thenReturn(cityDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cityDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.city_id", CoreMatchers.is(cityDto.getCity_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(cityDto.getCity())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CityController_DeleteCity_ReturnIsOk() throws Exception {
        doNothing().when(cityService).deleteCity(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cityDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}