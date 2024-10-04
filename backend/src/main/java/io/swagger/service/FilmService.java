package io.swagger.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.entity.Film;
import io.swagger.repository.FilmDTO;
import io.swagger.repository.FilmRepository;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    // Get all films
    public List<Film> getAllFilms() {
        Iterable<Film> films = filmRepository.findAll();
        return StreamSupport.stream(films.spliterator(), false)
                            .collect(Collectors.toList());//films.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void addFilms(List<Film> films) {
        filmRepository.saveAll(films);
    }

    // Get a film by ID
    public FilmDTO getFilmById(UUID id) {
        var filmId = filmRepository.findById(id);
        if (filmId.equals(null)) {
            throw new RuntimeException("Film not found");
        }
        if (filmId.isPresent()) {
            return convertToDTO(filmId.get());
        }
        return null;
    }

    // Add a new film
    public FilmDTO addFilm(Film f) {
        FilmDTO filmDTO = convertToDTO(f);
        Film film = convertToEntity(filmDTO);
        Film savedFilm = filmRepository.save(film);
        return convertToDTO(savedFilm);
    }

    // Update a film
    public Film updateFilm(UUID id, Film f) {
        FilmDTO filmDTO = convertToDTO(f);
        Optional<Film> film = filmRepository.findById(id);

        if (film.isEmpty()) {
            throw new RuntimeException("Film not found");
        }

        Film curFilm = film.get();

        // Update the entity with new values from the DTO
        curFilm.setTitle(filmDTO.title());
        curFilm.setMoodType(filmDTO.mood());
        curFilm.setLengthInMinutes(filmDTO.lengthInMinutes());

        Film updatedFilm = filmRepository.save(curFilm);
        return updatedFilm;
    }

    // Delete a film
    public void deleteFilm(UUID id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new RuntimeException("Film not found");
        }
    }

    // Conversion methods between Film and FilmDTO
    private FilmDTO convertToDTO(Film film) {
        return new FilmDTO(film.getId(), film.getTitle(), film.getLengthInMinutes(), film.getMoodType(), film.getReleaseYear());
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitle(filmDTO.title());
        film.setMoodType(filmDTO.mood());
        film.setLengthInMinutes(filmDTO.lengthInMinutes());
        return film;
    }
}
