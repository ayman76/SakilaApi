package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CustomerDto;
import com.example.sakilaapi.dto.CustomerRequestDto;
import com.example.sakilaapi.service.CustomerService;
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

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private static final String URL = "/api/v1/customers";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;
    private CustomerRequestDto customerRequestDto;
    private ApiResponse<CustomerDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        customerDto = CustomerDto.builder().customer_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").build();
        customerRequestDto = CustomerRequestDto.builder().customer_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").build();
        responseDto.setContent(Arrays.asList(customerDto, customerDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void CustomerController_CreateCustomer_ReturnIsCreated() throws Exception {
        when(customerService.createCustomer(Mockito.any(CustomerRequestDto.class))).thenReturn(customerDto);


        ResultActions response = mockMvc.perform(post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CustomerController_GetAllCustomers_ReturnIsOk() throws Exception {
        when(customerService.getAllCustomers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CustomerController_GetCustomerById_ReturnIsOk() throws Exception {
        when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(customerDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_id", CoreMatchers.is(customerDto.getCustomer_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CustomerController_GetCustomerByName_ReturnIsOk() throws Exception {
        when(customerService.getCustomerByName(Mockito.anyString())).thenReturn(customerDto);

        ResultActions response = mockMvc.perform(get(URL + "/ayman")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(customerDto.getFirst_name())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CustomerController_UpdateCustomer_ReturnIsOk() throws Exception {
        when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any(CustomerRequestDto.class))).thenReturn(customerDto);


        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CustomerController_DeleteCustomer_ReturnIsOk() throws Exception {
        doNothing().when(customerService).deleteCustomer(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}