package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.service.ActorService;
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

@WebMvcTest(controllers = ActorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    private static final String URL = "/api/v1/actors";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    @Autowired
    private ObjectMapper objectMapper;
    private ActorDto actorDto;


    @BeforeEach
    public void setup() {
        actorDto = ActorDto.builder().actor_id(1L).first_name("ayman").last_name("mohamed").build();
    }

    @Test
    public void ActorController_CreateActor_ReturnIsCreated() throws Exception {
        when(actorService.createActor(Mockito.any(ActorDto.class))).thenReturn(actorDto);

        ResultActions response = mockMvc.perform(post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ActorController_GetAllActors_ReturnIsOk() throws Exception {
        List<ActorDto> actorDtos = new ArrayList<>(Arrays.asList(actorDto, actorDto));
        when(actorService.getAllActors()).thenReturn(actorDtos);

        ResultActions response = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(actorDtos.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ActorController_GetActorById_ReturnIsOk() throws Exception {
        when(actorService.getActorById(Mockito.anyLong())).thenReturn(actorDto);

        ResultActions response = mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.actor_id", CoreMatchers.is(actorDto.getActor_id().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ActorController_UpdateActor_ReturnIsOk() throws Exception {
        when(actorService.updateActor(Mockito.anyLong(), Mockito.any(ActorDto.class))).thenReturn(actorDto);

        ResultActions response = mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.actor_id", CoreMatchers.is(actorDto.getActor_id().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(actorDto.getFirst_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", CoreMatchers.is(actorDto.getLast_name())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ActorController_DeleteActor_ReturnIsOk() throws Exception {
        doNothing().when(actorService).deleteActor(Mockito.anyLong());

        ResultActions response = mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}