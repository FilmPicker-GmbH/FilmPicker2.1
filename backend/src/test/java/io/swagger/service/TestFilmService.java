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
    FilmService filmServiceMock;

    @Mock
    FilmRepository filmRepositoryMock;

    private Film mockDataBuilder(String title) {
        int randLength = (int)(Math.random() * (maxFilmLength - minFilmLength + 1) + minFilmLength);
        String randYear = String.valueOf((int)(Math.random() * (maxYear - minYear + 1) + minYear));
        return Film.builder().id(UUID.randomUUID()).lengthInMinutes(randLength).releaseYear(randYear)
                .title(title).build();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockFilmDB = new ArrayList<Film>();
        for (int i = 0; i < movieCount; i++) {

            mockFilmDB.add(mockDataBuilder(String.valueOf(i)));
        }
    }

    @Test
    public void testWhenAddAll() {
            when(this.filmRepositoryMock.saveAll(Mockito.<Film>anyList())).thenReturn(mockFilmDB);

            filmServiceMock.addFilms(mockFilmDB);

            verify(filmRepositoryMock, Mockito.times(1)).saveAll(Mockito.<Film>anyList());
    }

    @Test
    public void testWhenGetAllFilms_ThenReturnAll() {
        when(this.filmRepositoryMock.findAll()).thenReturn(mockFilmDB);

        List<Film> result = filmServiceMock.getAllFilms();

        verify(filmRepositoryMock, Mockito.times(1)).findAll();
        assertEquals(15, result.size());
        assertArrayEquals(mockFilmDB.toArray(), result.toArray());
    }

    @Test
    public void testWhenGetFilmById_ThenFlow() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        UUID id = randFilmMock.getId();
        when(this.filmRepositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(randFilmMock));

        try {
            FilmDTO result = filmServiceMock.getFilmById(id);
            verify(filmRepositoryMock, Mockito.times(1)).findById(id);
            assertNotNull(result);
        } catch (Exception e) {
            
        }
    }

    @Test
    public void testWhenGetFilmByIdButIDNotExist_ThenThrow() {
        when(filmRepositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmServiceMock.getFilmById(UUID.randomUUID()));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    public void testWhenAddFilm_ThenFlow() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(false);
        when(filmRepositoryMock.save(Mockito.any(Film.class))).thenReturn(randFilmMock);

        filmServiceMock.addFilm(randFilmMock);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(randFilmMock);
    }

    @Test
    public void testWhenAddFilmAlreadyExist_ThenThrowError() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(true);

        Exception exception = assertThrows(EntityExistsException.class, () -> filmServiceMock.addFilm(randFilmMock));
        String expected = "Film already exist";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(0)).save(randFilmMock);
    }

    @Test
    public void testWhenUpdateFilm_ThenFlow() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(true);
        when(filmRepositoryMock.save(Mockito.any(Film.class))).thenReturn(randFilmMock);

        filmServiceMock.updateFilm(randFilmMock);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(randFilmMock);
    }

    @Test
    public void testWhenUpdateFilmButIDNotExist_ThenThrowError() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmServiceMock.updateFilm(randFilmMock));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(0)).save(randFilmMock);
    }

    @Test
    public void testWhenDeleteFilm_ThenFlow() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(true);
        doNothing().when(filmRepositoryMock).deleteById(randFilmMock.getId());

        filmServiceMock.deleteFilm(randFilmMock.getId());

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).deleteById(randFilmMock.getId());
    }

    @Test
    public void testWhenDeleteFilmButFilmNotExist_ThenThrow() {
        Film randFilmMock = mockFilmDB.get(new Random().nextInt(movieCount));
        when(filmRepositoryMock.existsById(Mockito.any(UUID.class))).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> filmServiceMock.deleteFilm(randFilmMock.getId()));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(randFilmMock.getId());
        verify(filmRepositoryMock, Mockito.times(0)).deleteById(randFilmMock.getId());
    }

}
