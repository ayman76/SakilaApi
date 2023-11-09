package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.FilmDto;
import com.example.sakilaapi.dto.InventoryDto;
import com.example.sakilaapi.dto.StoreDto;
import com.example.sakilaapi.service.InventoryService;
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

@WebMvcTest(controllers = InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    private static final String URL = "/api/v1/inventories";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private InventoryDto inventoryDto;
    private ApiResponse<InventoryDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        inventoryDto = InventoryDto.builder().inventory_id(1L).film(FilmDto.builder().film_id(1L).build()).store(StoreDto.builder().store_id(1L).build()).build();
        responseDto.setContent(Arrays.asList(inventoryDto, inventoryDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void InventoryController_CreateInventory_ReturnIsOk() throws Exception {
        when(inventoryService.createInventory(Mockito.any(InventoryDto.class))).thenReturn(inventoryDto);

        ResultActions response = mockMvc.perform(post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void InventoryController_GetAllInventories_ReturnIsOk() throws Exception {
        when(inventoryService.getAllInventories(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void InventoryController_GetInventoryById_ReturnIsOk() throws Exception {
        when(inventoryService.getInventoryById(Mockito.anyLong())).thenReturn(inventoryDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventory_id", CoreMatchers.is(inventoryDto.getInventory_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void InventoryController_UpdateInventory_ReturnIsOk() throws Exception {
        when(inventoryService.updateInventory(Mockito.anyLong(), Mockito.any(InventoryDto.class))).thenReturn(inventoryDto);

        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.film.film_id", CoreMatchers.is(inventoryDto.getFilm().getFilm_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.store.store_id", CoreMatchers.is(inventoryDto.getStore().getStore_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void InventoryController_DeleteInventory_ReturnIsOk() throws Exception {
        doNothing().when(inventoryService).deleteInventory(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(inventoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}