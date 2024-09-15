package io.swagger.service;

import io.swagger.repository.FilmDTO;
import io.swagger.model.Film;
import io.swagger.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    // Get all films
    public List<FilmDTO> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return films.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get a film by ID
    public FilmDTO getFilmById(Long id) {
        var filmId = filmRepository.findById(id);
        if (filmId.equals(null)) {
            throw new RuntimeException("Film not found");
        }
        return convertToDTO(filmId);
    }

    // Add a new film
    public FilmDTO addFilm(FilmDTO filmDTO) {
        Film film = convertToEntity(filmDTO);
        Film savedFilm = filmRepository.save(film);
        return convertToDTO(savedFilm);
    }

    // Update a film
    public FilmDTO updateFilm(Long id, FilmDTO filmDTO) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new RuntimeException("Film not found");
        }

        // Update the entity with new values from the DTO
        film.setTitle(filmDTO.title());
        film.setMoodType(filmDTO.mood());
        film.setLength(filmDTO.length());

        Film updatedFilm = filmRepository.save(film);
        return convertToDTO(updatedFilm);
    }

    // Delete a film
    public void deleteFilm(Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new RuntimeException("Film not found");
        }
    }

    // Conversion methods between Film and FilmDTO
    private FilmDTO convertToDTO(Film film) {
        return new FilmDTO(film.getId(), film.getTitle(), film.getLength(), film.getMoodType());
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitle(filmDTO.title());
        film.setMoodType(filmDTO.mood());
        film.setLength(filmDTO.length());
        return film;
    }
}
