package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.model.Actor;
import com.example.sakilaapi.repository.ActorRepository;
import com.example.sakilaapi.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ModelMapper modelMapper;
    private final ActorRepository actorRepository;

    @Override
    public ActorDto createActor(ActorDto actorDto) {
        Actor actor = modelMapper.map(actorDto, Actor.class);
        Actor savedActor = actorRepository.save(actor);
        return modelMapper.map(savedActor, ActorDto.class);
    }

    @Override
    public ApiResponse<ActorDto> getAllActors(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Actor> actors = actorRepository.findAll(pageable);
        List<Actor> listOfActors = actors.getContent();
        List<ActorDto> content = listOfActors.stream().map(c -> modelMapper.map(c, ActorDto.class)).toList();


        return getApiResponse(pageNo, pageSize, content, actors);
    }

    @Override
    public ActorDto getActorById(Long id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Actor"));
        return modelMapper.map(actor, ActorDto.class);
    }

    @Override
    public ActorDto updateActor(Long id, ActorDto actorDto) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Actor"));
        actor.setFirst_name(actorDto.getFirst_name());
        actor.setLast_name(actorDto.getLast_name());

        Actor udpatedActor = actorRepository.save(actor);
        return modelMapper.map(udpatedActor, ActorDto.class);
    }

    @Override
    public void deleteActor(Long id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Actor"));
        actorRepository.delete(actor);
    }
}
