package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.model.Actor;
import com.example.sakilaapi.repository.ActorRepository;
import com.example.sakilaapi.service.impl.ActorServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    private static final Long ACTOR_ID = 1L;
    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Actor actor;
    private ActorDto actorDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        actorService = new ActorServiceImpl(modelMapper, actorRepository);
        actor = Actor.builder().actor_id(1L).first_name("ayman").last_name("mohamed").build();
        actorDto = ActorDto.builder().actor_id(1L).first_name("ayman").last_name("mohamed").build();
    }

    @Test
    public void ActorService_CreateActor_ReturnActorDto() {

        when(actorRepository.save(Mockito.any(Actor.class))).thenReturn(actor);

        ActorDto savedActor = actorService.createActor(actorDto);

        Assertions.assertThat(savedActor).isNotNull();
    }

    @Test
    public void ActorService_GetAllActors_ReturnActorDtos() {

        Page<Actor> actors = Mockito.mock(Page.class);

        when(actorRepository.findAll(Mockito.any(Pageable.class))).thenReturn(actors);

        ApiResponse<ActorDto> response = actorService.getAllActors(0, 1);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void ActorService_GetActorById_ReturnActorDto() {

        when(actorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(actor));

        ActorDto foundedActor = actorService.getActorById(ACTOR_ID);

        Assertions.assertThat(foundedActor).isNotNull();
        Assertions.assertThat(foundedActor.getActor_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void ActorService_UpdateActor_ReturnActorDto() {

        when(actorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(actor));
        when(actorRepository.save(Mockito.any(Actor.class))).thenReturn(actor);

        ActorDto updatedActor = actorService.updateActor(ACTOR_ID, actorDto);

        Assertions.assertThat(updatedActor).isNotNull();
        Assertions.assertThat(updatedActor.getActor_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void ActorService_DeleteActor_ReturnActorDto() {

        when(actorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(actor));

        assertAll(() -> actorService.deleteActor(ACTOR_ID));
    }

}