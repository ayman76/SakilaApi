package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.service.AddressService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    private static final String URL = "/api/v1/address";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;
    private AddressDto addressDto;


    @BeforeEach
    public void setup() {
        addressDto = AddressDto.builder().address_id(1L).address("address").address2("address")
                .district("district").city(new CityDto()).phone("011235515").postal_code("0321513").build();
    }

    @Test
    public void AddressController_CreateAddress_ReturnIsCreated() throws Exception {
        when(addressService.createAddress(Mockito.any(AddressDto.class))).thenReturn(addressDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AddressController_GetAllAddresss_ReturnIsOk() throws Exception {
        List<AddressDto> addressDtos = new ArrayList<>(Arrays.asList(addressDto, addressDto));
        when(addressService.getAllAddresses()).thenReturn(addressDtos);

        ResultActions response = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(addressDtos.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AddressController_GetAddressById_ReturnIsOk() throws Exception {
        when(addressService.getAddressById(Mockito.anyLong())).thenReturn(addressDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address_id", CoreMatchers.is(addressDto.getAddress_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AddressController_UpdateAddress_ReturnIsOk() throws Exception {
        when(addressService.updateAddress(Mockito.anyLong(), Mockito.any(AddressDto.class))).thenReturn(addressDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address_id", CoreMatchers.is(addressDto.getAddress_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(addressDto.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address2", CoreMatchers.is(addressDto.getAddress2())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.district", CoreMatchers.is(addressDto.getDistrict())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(addressDto.getPhone())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postal_code", CoreMatchers.is(addressDto.getPostal_code())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AddressController_DeleteAddress_ReturnIsOk() throws Exception {
        doNothing().when(addressService).deleteAddress(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}