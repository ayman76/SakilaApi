package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ActorDto;
import com.example.sakilaapi.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/actors")
public class ActorController {
    private final ActorService actorService;

    @PostMapping("/create")
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorDto actorDto) {
        return new ResponseEntity<>(actorService.createActor(actorDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return new ResponseEntity<>(actorService.getAllActors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {
        return new ResponseEntity<>(actorService.getActorById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDto> updateActor(@PathVariable Long id, @RequestBody ActorDto actorDto) {
        return new ResponseEntity<>(actorService.updateActor(id, actorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActorDto> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
