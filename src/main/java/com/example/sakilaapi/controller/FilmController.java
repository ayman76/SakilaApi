package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping("/create")
    public ResponseEntity<FilmDto> createFilm(@RequestBody FilmRequestDto filmDto) {
        return new ResponseEntity<>(filmService.createFilm(filmDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<FilmDto>> getAllFilms(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(filmService.getAllFilms(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDto> updateFilm(@PathVariable Long id, @RequestBody FilmRequestDto filmDto) {
        return new ResponseEntity<>(filmService.updateFilm(id, filmDto), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<FilmDto>> getFilmsByLength(@RequestParam(name = "length") int length,
                                                                 @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(filmService.getFilmsByLength(length, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<CategoryDto>> getFilmsCategories(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.getFilmsCategories(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/actors")
    public ResponseEntity<List<ActorDto>> getFilmsActors(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.getFilmsActors(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmDto> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
