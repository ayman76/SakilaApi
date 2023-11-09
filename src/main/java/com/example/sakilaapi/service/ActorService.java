package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.dto.ApiResponse;

public interface ActorService {

    ActorDto createActor(ActorDto actorDto);

    ApiResponse<ActorDto> getAllActors(int pageNo, int pageSize);

    ActorDto getActorById(Long id);

    ActorDto updateActor(Long id, ActorDto actorDto);

    void deleteActor(Long id);
}
