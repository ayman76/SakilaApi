package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.*;

import java.util.List;

public interface FilmService {
    FilmDto createFilm(FilmRequestDto filmDto);

    ApiResponse<FilmDto> getAllFilms(int pageNo, int pageSize);

    FilmDto getFilmById(Long id);

    FilmDto updateFilm(Long id, FilmRequestDto filmDto);

    ApiResponse<FilmDto> getFilmsByLength(int length, int pageNo, int pageSize);

    List<CategoryDto> getFilmsCategories(Long id);

    List<ActorDto> getFilmsActors(Long id);

    void deleteFilm(Long id);
}
