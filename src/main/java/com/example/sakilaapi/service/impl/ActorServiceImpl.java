package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.model.Actor;
import com.example.sakilaapi.repository.ActorRepository;
import com.example.sakilaapi.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ActorDto> getAllActors() {
        return actorRepository.findAll().stream().map(a -> modelMapper.map(a, ActorDto.class)).collect(Collectors.toList());
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
