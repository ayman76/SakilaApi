package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.Rating;
import com.example.sakilaapi.service.FilmService;
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

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = FilmController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    private static final String URL = "/api/v1/films";
    ApiResponse<FilmDto> responseDto;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FilmService filmService;
    @Autowired
    private ObjectMapper objectMapper;
    private FilmDto filmDto;

    @BeforeEach
    public void setup() {
        responseDto = new ApiResponse<>();
        filmDto = FilmDto.builder().film_id(1L).title("title").description("description").releaseYear(Year.of(2006)).rating(Rating.PG).rentalRate(122).rentalDuration(12).length(55).replacementCost(0.22).specialFeatures("feature").language(new LanguageDto()).originalLanguage(new LanguageDto()).build();
        responseDto.setContent(Arrays.asList(filmDto, filmDto));
        responseDto.setPageSize(10);
        responseDto.setPageNo(0);
        responseDto.setTotalPages(10);
        responseDto.setTotalElements(100L);
        responseDto.setLast(false);
    }

    @Test
    public void FilmController_CreateFilm_ReturnIsCreated() throws Exception {
        when(filmService.createFilm(Mockito.any(FilmRequestDto.class))).thenReturn(filmDto);

        ResultActions response = mockMvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(filmDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_GetAllFilms_ReturnIsOk() throws Exception {


        when(filmService.getAllFilms(Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_GetFilmById_ReturnIsOk() throws Exception {
        when(filmService.getFilmById(Mockito.anyLong())).thenReturn(filmDto);

        ResultActions response = mockMvc.perform(get(URL + "/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.film_id", CoreMatchers.is(filmDto.getFilm_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_UpdateFilm_ReturnIsOk() throws Exception {
        when(filmService.updateFilm(Mockito.anyLong(), Mockito.any(FilmRequestDto.class))).thenReturn(filmDto);

        ResultActions response = mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(filmDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.film_id", CoreMatchers.is(filmDto.getFilm_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(filmDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(filmDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear", CoreMatchers.is(filmDto.getReleaseYear().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalDuration", CoreMatchers.is(filmDto.getRentalDuration())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalRate", CoreMatchers.is(filmDto.getRentalRate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length", CoreMatchers.is(filmDto.getLength())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.replacementCost", CoreMatchers.is(filmDto.getReplacementCost())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", CoreMatchers.is(filmDto.getRating().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialFeatures", CoreMatchers.is(filmDto.getSpecialFeatures())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_GetFilmsByLength_ReturnIsOk() throws Exception {
        when(filmService.getFilmsByLength(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("length", "80")
                .param("pageNo", "0")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_GetFilmsCategories_ReturnIsOk() throws Exception {
        List<CategoryDto> categoryDtos = new ArrayList<>(Arrays.asList(new CategoryDto(), new CategoryDto()));
        when(filmService.getFilmsCategories(Mockito.anyLong())).thenReturn(categoryDtos);

        ResultActions response = mockMvc.perform(get(URL + "/1/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(filmDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_GetFilmsActors_ReturnIsOk() throws Exception {
        List<ActorDto> actorDtos = new ArrayList<>(Arrays.asList(new ActorDto(), new ActorDto()));
        when(filmService.getFilmsActors(Mockito.anyLong())).thenReturn(actorDtos);

        ResultActions response = mockMvc.perform(get(URL + "/1/actors").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(filmDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void FilmController_DeleteFilm_ReturnIsOk() throws Exception {
        doNothing().when(filmService).deleteFilm(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(filmDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}