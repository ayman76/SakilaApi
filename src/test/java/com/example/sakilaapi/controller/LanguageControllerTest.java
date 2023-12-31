package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.LanguageDto;
import com.example.sakilaapi.service.LanguageService;
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

@WebMvcTest(controllers = LanguageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LanguageControllerTest {

    private static final String URL = "/api/v1/languages";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageService languageService;

    @Autowired
    private ObjectMapper objectMapper;
    private LanguageDto languageDto;

    private ApiResponse<LanguageDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        languageDto = LanguageDto.builder().language_id(1L).name("language").build();
        responseDto.setContent(Arrays.asList(languageDto, languageDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void LanguageController_CreateLanguage_ReturnIsCreated() throws Exception {
        when(languageService.createLanguage(Mockito.any(LanguageDto.class))).thenReturn(languageDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(languageDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void LanguageController_GetAllLanguages_ReturnIsOk() throws Exception {

        when(languageService.getAllLanguages(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void LanguageController_GetLanguageById_ReturnIsOk() throws Exception {
        when(languageService.getLanguageById(Mockito.anyLong())).thenReturn(languageDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.language_id", CoreMatchers.is(languageDto.getLanguage_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void LanguageController_UpdateLanguage_ReturnIsOk() throws Exception {
        when(languageService.updateLanguage(Mockito.anyLong(), Mockito.any(LanguageDto.class))).thenReturn(languageDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(languageDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.language_id", CoreMatchers.is(languageDto.getLanguage_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(languageDto.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void LanguageController_DeleteLanguage_ReturnIsOk() throws Exception {
        doNothing().when(languageService).deleteLanguage(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(languageDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}