package com.example.sakilaapi.repository;

import com.example.sakilaapi.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    Page<Film> findFilmsByLength(Pageable pageable, int length);
}
