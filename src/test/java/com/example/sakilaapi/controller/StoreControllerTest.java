package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.dto.StoreDto;
import com.example.sakilaapi.service.StoreService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = StoreController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    private static final String URL = "/api/v1/stores";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    private StoreDto storeDto;
    private ApiResponse<StoreDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        storeDto = StoreDto.builder().store_id(1L).staff(new StaffDto()).address(new AddressDto()).build();
        responseDto.setContent(Arrays.asList(storeDto, storeDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void StoreController_GetAllStores_ReturnIsOk() throws Exception {
        when(storeService.getAllStores(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void StoreController_GetStoreById_ReturnIsOk() throws Exception {
        when(storeService.getStoreById(Mockito.anyLong())).thenReturn(storeDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.store_id", CoreMatchers.is(storeDto.getStore_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }


}