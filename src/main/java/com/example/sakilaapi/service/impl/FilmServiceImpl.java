package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.Actor;
import com.example.sakilaapi.model.Category;
import com.example.sakilaapi.model.Film;
import com.example.sakilaapi.model.Language;
import com.example.sakilaapi.repository.ActorRepository;
import com.example.sakilaapi.repository.CategoryRepository;
import com.example.sakilaapi.repository.FilmRepository;
import com.example.sakilaapi.repository.LanguageRepository;
import com.example.sakilaapi.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;



    @Override
    public FilmDto createFilm(FilmRequestDto filmDto) {
        Language language = languageRepository.findById(filmDto.getLanguage().getLanguage_id()).orElseThrow(() -> new RuntimeException("Not Founded Language with id: " + filmDto.getLanguage().getLanguage_id()));
        Language originalLanguage = languageRepository.findById(filmDto.getOriginalLanguage().getLanguage_id()).orElseThrow(() -> new RuntimeException("Not Founded Original Language with id: " + filmDto.getOriginalLanguage().getLanguage_id()));
        Set<Actor> actors = filmDto.getActors().stream().map(a -> actorRepository.findById(a.getActor_id()).orElseThrow(() -> new RuntimeException("Not Founded Actor with id: " + a.getActor_id()))).collect(Collectors.toSet());
        Set<Category> categories = filmDto.getCategories().stream().map(c -> categoryRepository.findById(c.getCategory_id()).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + c.getCategory_id()))).collect(Collectors.toSet());

        Film film = modelMapper.map(filmDto, Film.class);

        film.setActors(actors);
        film.setCategories(categories);
        film.setLanguage(language);
        film.setOriginalLanguage(originalLanguage);
        Film savedFilm = filmRepository.save(film);
        return modelMapper.map(savedFilm, FilmDto.class);
    }

    @Override
    public ApiResponse<FilmDto> getAllFilms(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Film> films = filmRepository.findAll(pageable);
        List<Film> listOfFilms = films.getContent();
        List<FilmDto> content = listOfFilms.stream().map(c -> modelMapper.map(c, FilmDto.class)).toList();


        return getApiResponse(pageNo, pageSize, content, films);
    }

    @Override
    public FilmDto getFilmById(Long id) {
        Film foundedFilm = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + id));
        return modelMapper.map(foundedFilm, FilmDto.class);
    }

    @Override
    public FilmDto updateFilm(Long id, FilmRequestDto filmDto) {
        Film foundedFilm = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + id));
        Language language = languageRepository.findById(filmDto.getLanguage().getLanguage_id()).orElseThrow(() -> new RuntimeException("Not Founded Language with id: " + filmDto.getLanguage().getLanguage_id()));
        Language originalLanguage = languageRepository.findById(filmDto.getOriginalLanguage().getLanguage_id()).orElseThrow(() -> new RuntimeException("Not Founded Original Language with id: " + filmDto.getOriginalLanguage().getLanguage_id()));
        Set<Actor> actors = filmDto.getActors().stream().map(a -> actorRepository.findById(a.getActor_id()).orElseThrow(() -> new RuntimeException("Not Founded Actor with id: " + a.getActor_id()))).collect(Collectors.toSet());
        Set<Category> categories = filmDto.getCategories().stream().map(c -> categoryRepository.findById(c.getCategory_id()).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + c.getCategory_id()))).collect(Collectors.toSet());
        foundedFilm.setTitle(filmDto.getTitle());
        foundedFilm.setDescription(filmDto.getDescription());
        foundedFilm.setReleaseYear(filmDto.getReleaseYear());
        foundedFilm.setRentalDuration(filmDto.getRentalDuration());
        foundedFilm.setRentalRate(filmDto.getRentalRate());
        foundedFilm.setLength(filmDto.getLength());
        foundedFilm.setReplacementCost(filmDto.getReplacementCost());
        foundedFilm.setRating(filmDto.getRating());
        foundedFilm.setSpecialFeatures(filmDto.getSpecialFeatures());
        foundedFilm.setLanguage(language);
        foundedFilm.setOriginalLanguage(originalLanguage);
        foundedFilm.setActors(actors);
        foundedFilm.setCategories(categories);
        Film updateFilm = filmRepository.save(foundedFilm);
        return modelMapper.map(updateFilm, FilmDto.class);
    }

    @Override
    public ApiResponse<FilmDto> getFilmsByLength(int length, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Film> films = filmRepository.findFilmsByLength(pageable, length);
        List<Film> listOfFilms = films.getContent();
        List<FilmDto> content = listOfFilms.stream().map(c -> modelMapper.map(c, FilmDto.class)).toList();

        return getApiResponse(pageNo, pageSize, content, films);
    }

    @Override
    public List<CategoryDto> getFilmsCategories(Long id) {
        Film foundedFilm = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + id));
        return foundedFilm.getCategories().stream().map(c -> modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ActorDto> getFilmsActors(Long id) {
        Film foundedFilm = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + id));
        return foundedFilm.getActors().stream().map(a -> modelMapper.map(a, ActorDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteFilm(Long id) {
        Film foundedFilm = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + id));
        filmRepository.delete(foundedFilm);
    }
}
