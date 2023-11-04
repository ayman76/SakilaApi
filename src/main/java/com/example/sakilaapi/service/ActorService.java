package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ActorDto;

import java.util.List;

public interface ActorService {

    ActorDto createActor(ActorDto actorDto);

    List<ActorDto> getAllActors();

    ActorDto getActorById(Long id);

    ActorDto updateActor(Long id, ActorDto actorDto);

    void deleteActor(Long id);
}
