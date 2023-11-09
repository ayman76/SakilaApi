package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.service.StaffService;
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

@WebMvcTest(controllers = StaffController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    private static final String URL = "/api/v1/staffs";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService;

    private StaffDto staffDto;
    private ApiResponse<StaffDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        staffDto = StaffDto.builder().staff_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").password("password").username("userName").build();
        responseDto.setContent(Arrays.asList(staffDto, staffDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void StaffController_GetAllStaffs_ReturnIsOk() throws Exception {
        when(staffService.getAllStaffs(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void StaffController_GetStaffById_ReturnIsOk() throws Exception {
        when(staffService.getStaffById(Mockito.anyLong())).thenReturn(staffDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staff_id", CoreMatchers.is(staffDto.getStaff_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void StaffController_GetStaffByName_ReturnIsOk() throws Exception {
        when(staffService.getStaffByName(Mockito.anyString())).thenReturn(staffDto);

        ResultActions response = mockMvc.perform(get(URL + "/ayman")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(staffDto.getFirst_name())))
                .andDo(MockMvcResultHandlers.print());
    }


}