package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CategoryDto;
import com.example.sakilaapi.service.CategoryService;
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

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private static final String URL = "/api/v1/categories";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;
    private CategoryDto categoryDto;

    private ApiResponse<CategoryDto> responseDto;


    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        categoryDto = CategoryDto.builder().category_id(1L).name("category").build();
        responseDto.setContent(Arrays.asList(categoryDto, categoryDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void CategoryController_CreateCategory_ReturnIsCreated() throws Exception {
        when(categoryService.createCategory(Mockito.any(CategoryDto.class))).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_GetAllCategorys_ReturnIsOk() throws Exception {
        when(categoryService.getAllCategories(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size()))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_GetCategoryById_ReturnIsOk() throws Exception {
        when(categoryService.getCategoryById(Mockito.anyLong())).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.category_id", CoreMatchers.is(categoryDto.getCategory_id().intValue()))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_UpdateCategory_ReturnIsOk() throws Exception {
        when(categoryService.updateCategory(Mockito.anyLong(), Mockito.any(CategoryDto.class))).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.category_id", CoreMatchers.is(categoryDto.getCategory_id().intValue()))).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryDto.getName()))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_DeleteCategory_ReturnIsOk() throws Exception {
        doNothing().when(categoryService).deleteCategory(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}