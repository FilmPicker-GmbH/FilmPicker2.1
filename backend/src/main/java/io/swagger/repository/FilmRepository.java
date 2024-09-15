package io.swagger.repository;

import io.swagger.model.Film;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRepository {
    private List<Film> films;

    public FilmRepository() {
        films = List.of(
                new Film()
        );
    }

    public List<Film> findAll() {
        return films;
    }

    public Film findById(Long id) {
        return films.stream()
                .filter(film -> film.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Film save(Film film) {
        films.add(film);
        return film;
    }

    public void deleteById(Long id) {
        films.removeIf(film -> film.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return films.stream().anyMatch(film -> film.getId().equals(id));
    }
}
