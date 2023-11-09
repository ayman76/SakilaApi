package com.example.sakilaapi.dto;

import com.example.sakilaapi.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private Long film_id;
    private String title;
    private String description;
    private Year releaseYear;
    private Integer rentalDuration;
    private Integer rentalRate;
    private Integer length;
    private Double replacementCost;
    private Rating rating;
    private String specialFeatures;
    private LanguageDto language;
    private LanguageDto originalLanguage;
//    private Set<ActorDto> actors;
//    private Set<CategoryDto> categories;
}
