package io.swagger.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.swagger.entity.Film;
import io.swagger.repository.FilmDTO;
import io.swagger.repository.FilmRepository;
import jakarta.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
public class TestFilmService {

    private List<Film> mockFilmDB;
    private int maxFilmLength = 200;
    private int minFilmLength = 50;
    private int minYear = 1990;
    private int maxYear = 2024;
    private int movieCount = 15;

    @InjectMocks
    FilmService filmService;

    @Mock
    FilmRepository filmRepository;

    @BeforeEach
    public void initData() {
        mockFilmDB = new ArrayList<Film>();
        for (int i = 0; i < movieCount; i++) {

            mockFilmDB.add(mockDataBuilder(String.valueOf(i)));
        }
    }

    private Film mockDataBuilder(String title) {
        int randLength = (int)(Math.random() * (maxFilmLength - minFilmLength + 1) + minFilmLength);
        String randYear = String.valueOf((int)(Math.random() * (maxYear - minYear + 1) + minYear));
        return Film.builder().id(UUID.randomUUID()).lengthInMinutes(randLength).releaseYear(randYear)
                .title(title).build();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWhenAddAll() {
            when(this.filmRepository.saveAll(Mockito.<Film>anyList())).thenReturn(mockFilmDB);

            filmService.addFilms(mockFilmDB);

            verify(filmRepository, Mockito.times(1)).saveAll(Mockito.<Film>anyList());
    }

    @Test
    public void testWhenGetAllFilms_ThenReturnAll() {
        when(this.filmRepository.findAll()).thenReturn(mockFilmDB);

        List<Film> result = filmService.getAllFilms();

        verify(filmRepository, Mockito.times(1)).findAll();
        assertEquals(15, result.size());
        assertArrayEquals(mockFilmDB.toArray(), result.toArray());
    }

    @Test
    public void testWhenGetFilmById_ThenFlow() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        UUID id = randFilm.getId();
        when(this.filmRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(randFilm));

        try {
            FilmDTO result = filmService.getFilmById(id);
            verify(filmRepository, Mockito.times(1)).findById(id);
            assertNotNull(result);
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testWhenGetFilmByIdButIDNotExist_ThenThrow() {
        when(filmRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmService.getFilmById(UUID.randomUUID()));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    public void testWhenAddFilm_ThenFlow() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);
        when(filmRepository.save(Mockito.any(Film.class))).thenReturn(randFilm);

        filmService.addFilm(randFilm);

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(1)).save(randFilm);
    }

    @Test
    public void testWhenAddFilmAlreadyExist_ThenThrowError() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);

        Exception exception = assertThrows(EntityExistsException.class, () -> filmService.addFilm(randFilm));
        String expected = "Film already exist";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(0)).save(randFilm);
    }

    @Test
    public void testWhenUpdateFilm_ThenFlow() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);
        when(filmRepository.save(Mockito.any(Film.class))).thenReturn(randFilm);

        filmService.updateFilm(randFilm);

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(1)).save(randFilm);
    }

    @Test
    public void testWhenUpdateFilmNotExist_ThenThrowError() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmService.updateFilm(randFilm));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(0)).save(randFilm);
    }

    @Test
    public void testWhenDeleteFilm_ThenFlow() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);
        doNothing().when(filmRepository).deleteById(randFilm.getId());

        filmService.deleteFilm(randFilm.getId());

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(1)).deleteById(randFilm.getId());
    }

    @Test
    public void testWhenDeleteFilmNonExistant_ThenThrow() {
        Film randFilm = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmService.deleteFilm(randFilm.getId()));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepository, Mockito.times(1)).existsById(randFilm.getId());
        verify(filmRepository, Mockito.times(0)).deleteById(randFilm.getId());
    }

}
