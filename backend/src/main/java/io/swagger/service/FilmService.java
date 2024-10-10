package io.swagger.service;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.entity.Film;
import io.swagger.repository.FilmDTO;
import io.swagger.repository.FilmRepository;
import jakarta.persistence.EntityExistsException;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    // Get all films
    public List<Film> getAllFilms() {
        Iterable<Film> films = filmRepository.findAll();
        return StreamSupport.stream(films.spliterator(), false)
                            .collect(Collectors.toList());
    }

    public void addFilms(List<Film> films) {
        filmRepository.saveAll(films);
    }

    // Get a film by ID
    public FilmDTO getFilmById(UUID id) throws NoSuchFileException {
        Optional<Film> filmId = filmRepository.findById(id);
        if (filmId.isPresent()) {
            return convertToDTO(filmId.get());
        }
            
        throw new NoSuchElementException("Film not found");
    }

    // Add a new film
    public FilmDTO addFilm(Film f) {
        if (filmRepository.existsById(f.getId())) {
            throw new EntityExistsException("Film already exist");
        }
        Film savedFilm = filmRepository.save(f);
        return convertToDTO(savedFilm);
    }

    // Update a film
    public Film updateFilm(Film film) {
        if (!filmRepository.existsById(film.getId())) {
            throw new NoSuchElementException("Film not found");
        }
        Film updatedFilm = filmRepository.save(film);
        return updatedFilm;
    }

    // Delete a film
    public void deleteFilm(UUID id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Film not found");
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
