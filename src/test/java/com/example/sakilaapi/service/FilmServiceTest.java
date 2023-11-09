package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.*;
import com.example.sakilaapi.repository.ActorRepository;
import com.example.sakilaapi.repository.CategoryRepository;
import com.example.sakilaapi.repository.FilmRepository;
import com.example.sakilaapi.repository.LanguageRepository;
import com.example.sakilaapi.service.impl.FilmServiceImpl;
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

import java.time.Year;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {


    private static final Long LANGUAGE_ID = 1L;
    @Mock
    private FilmRepository filmRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FilmServiceImpl filmService;

    private Film film;
    private FilmRequestDto filmRequestDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        filmService = new FilmServiceImpl(modelMapper, filmRepository, languageRepository, actorRepository, categoryRepository);
        film = Film.builder().film_id(1L).title("title").description("description").releaseYear(Year.of(2006)).rating(Rating.PG).rentalRate(122).rentalDuration(12).length(55).replacementCost(0.22).specialFeatures("feature").actors(Set.of(Actor.builder().actor_id(1L).first_name("ayman").last_name("mohamed").build(), Actor.builder().actor_id(2L).first_name("ayman").last_name("mohamed").build())).categories(Set.of(Category.builder().category_id(1L).name("category").build(), Category.builder().category_id(2L).name("category").build())).language(Language.builder().language_id(1L).name("lang1").build()).originalLanguage(Language.builder().language_id(2L).name("lang2").build()).build();
        filmRequestDto = FilmRequestDto.builder().title("title").description("description").releaseYear(Year.of(2006)).rating(Rating.PG).rentalRate(122).rentalDuration(12).length(55).replacementCost(0.22).specialFeatures("feature").actors(Set.of(ActorDto.builder().actor_id(1L).first_name("ayman").last_name("mohamed").build(), ActorDto.builder().actor_id(2L).first_name("ayman").last_name("mohamed").build())).categories(Set.of(CategoryDto.builder().category_id(1L).name("category").build(), CategoryDto.builder().category_id(2L).name("category").build())).language(LanguageDto.builder().language_id(1L).name("lang1").build()).originalLanguage(LanguageDto.builder().language_id(2L).name("lang2").build()).build();
    }

    @Test
    public void FilmService_CreateFilm_ReturnFilmDto() {


        when(languageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Language()));
        when(actorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Actor()));
        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Category()));

        when(filmRepository.save(Mockito.any(Film.class))).thenReturn(film);

        FilmDto savedFilm = filmService.createFilm(filmRequestDto);

        Assertions.assertThat(savedFilm).isNotNull();
    }

    @Test
    public void FilmService_GetAllFilms_ReturnFilmDtos() {

        Page<Film> films = Mockito.mock(Page.class);

        when(filmRepository.findAll(Mockito.any(Pageable.class))).thenReturn(films);

        ApiResponse<FilmDto> response = filmService.getAllFilms(1, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void FilmService_GetAllFilmsByLength_ReturnFilmDtos() {

        Page<Film> films = Mockito.mock(Page.class);

        when(filmRepository.findFilmsByLength(Mockito.any(Pageable.class), Mockito.anyInt())).thenReturn(films);

        ApiResponse<FilmDto> response = filmService.getFilmsByLength(80, 0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void FilmService_GetFilmById_ReturnFilmDto() {

        when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(film));

        FilmDto foundedFilm = filmService.getFilmById(LANGUAGE_ID);

        Assertions.assertThat(foundedFilm).isNotNull();
        Assertions.assertThat(foundedFilm.getFilm_id()).isEqualTo(LANGUAGE_ID);
    }

    @Test
    public void FilmService_UpdateFilm_ReturnFilmDto() {

        when(languageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Language()));
        when(actorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Actor()));
        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Category()));
        when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(film));
        when(filmRepository.save(Mockito.any(Film.class))).thenReturn(film);

        FilmDto updatedFilm = filmService.updateFilm(LANGUAGE_ID, filmRequestDto);

        Assertions.assertThat(updatedFilm).isNotNull();
        Assertions.assertThat(updatedFilm.getFilm_id()).isEqualTo(LANGUAGE_ID);
    }

    @Test
    public void FilmService_DeleteFilm_ReturnFilmDto() {

        when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(film));

        assertAll(() -> filmService.deleteFilm(LANGUAGE_ID));
    }

}