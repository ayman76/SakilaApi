package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.service.PaymentService;
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

@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private static final String URL = "/api/v1/payments";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentDto paymentDto;
    private ApiResponse<PaymentDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        paymentDto = PaymentDto.builder().payment_id(1L)
                .staff(StaffDto.builder().staff_id(1L).build())
                .customer(CustomerDto.builder().customer_id(1L).build())
                .rental(RentalDto.builder().rental_id(1L).build()).build();
        responseDto.setContent(Arrays.asList(paymentDto, paymentDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void PaymentController_CreatePayment_ReturnIsOk() throws Exception {
        when(paymentService.createPayment(Mockito.any(PaymentDto.class))).thenReturn(paymentDto);

        ResultActions response = mockMvc.perform(post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PaymentController_GetAllInventories_ReturnIsOk() throws Exception {
        when(paymentService.getAllPayments(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PaymentController_GetPaymentById_ReturnIsOk() throws Exception {
        when(paymentService.getPaymentById(Mockito.anyLong())).thenReturn(paymentDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payment_id", CoreMatchers.is(paymentDto.getPayment_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PaymentController_UpdatePayment_ReturnIsOk() throws Exception {
        when(paymentService.updatePayment(Mockito.anyLong(), Mockito.any(PaymentDto.class))).thenReturn(paymentDto);

        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staff.staff_id", CoreMatchers.is(paymentDto.getStaff().getStaff_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customer_id", CoreMatchers.is(paymentDto.getCustomer().getCustomer_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rental.rental_id", CoreMatchers.is(paymentDto.getRental().getRental_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PaymentController_DeletePayment_ReturnIsOk() throws Exception {
        doNothing().when(paymentService).deletePayment(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}