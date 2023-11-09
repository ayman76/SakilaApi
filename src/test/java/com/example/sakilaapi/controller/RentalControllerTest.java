package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.service.RentalService;
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

@WebMvcTest(controllers = RentalController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RentalControllerTest {

    private static final String URL = "/api/v1/rentals";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    private RentalDto rentalDto;
    private ApiResponse<RentalDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        rentalDto = RentalDto.builder().rental_id(1L)
                .staff(StaffDto.builder().staff_id(1L).build())
                .customer(CustomerDto.builder().customer_id(1L).build())
                .inventory(InventoryDto.builder().inventory_id(1L).build()).build();
        responseDto.setContent(Arrays.asList(rentalDto, rentalDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void RentalController_CreateRental_ReturnIsOk() throws Exception {
        when(rentalService.createRental(Mockito.any(RentalDto.class))).thenReturn(rentalDto);

        ResultActions response = mockMvc.perform(post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentalDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void RentalController_GetAllInventories_ReturnIsOk() throws Exception {
        when(rentalService.getAllRentals(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void RentalController_GetRentalById_ReturnIsOk() throws Exception {
        when(rentalService.getRentalById(Mockito.anyLong())).thenReturn(rentalDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rental_id", CoreMatchers.is(rentalDto.getRental_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void RentalController_UpdateRental_ReturnIsOk() throws Exception {
        when(rentalService.updateRental(Mockito.anyLong(), Mockito.any(RentalDto.class))).thenReturn(rentalDto);

        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentalDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staff.staff_id", CoreMatchers.is(rentalDto.getStaff().getStaff_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customer_id", CoreMatchers.is(rentalDto.getCustomer().getCustomer_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventory.inventory_id", CoreMatchers.is(rentalDto.getInventory().getInventory_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void RentalController_DeleteRental_ReturnIsOk() throws Exception {
        doNothing().when(rentalService).deleteRental(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rentalDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}