package io.swagger.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.swagger.MockHelper;
import io.swagger.entity.Film;
import io.swagger.mockrepository.MockRepository;
import io.swagger.repository.FilmRepository;
import jakarta.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
public class TestFilmService {

    @InjectMocks
    FilmService filmServiceMock;

    @Mock
    FilmRepository filmRepositoryMock;

    MockRepository<Film, UUID> mockRepository;

    UUID mockUUID1 = UUID.fromString("47cdcd3f-405a-4b84-b340-58a0b5a234a7");
    UUID mockUUID2 = UUID.fromString("93216a10-0370-4541-8533-462c2cb18dc1");
    UUID mockUUID3 = UUID.fromString("9171ab44-5089-44c4-a144-e79d7f5acef9");

    @BeforeEach
    public void init() {
        mockRepository = new MockRepository<>();
        MockitoAnnotations.openMocks(this);
        MockHelper.setupMockRepository(filmRepositoryMock, mockRepository);
    }

    @AfterEach
    public void cleanUp() {
        mockRepository.clear();
    }

    private List<Film> createListOfFilms() {
        Film mockFilm1 = new Film();
        mockFilm1.setId(mockUUID1);
        mockFilm1.setTitle("Prisoner of my Heart");
        mockFilm1.setLengthInMinutes(113);
        mockFilm1.setReleaseYear("2005");

        Film mockFilm2 = new Film();
        mockFilm2.setId(mockUUID2);
        mockFilm2.setTitle("Avenger: Finite War");
        mockFilm2.setLengthInMinutes(210);
        mockFilm2.setReleaseYear("2022");

        Film mockFilm3 = new Film();
        mockFilm3.setId(mockUUID3);
        mockFilm3.setTitle("Apfel: Steve Arbeit");
        mockFilm3.setLengthInMinutes(95);
        mockFilm3.setReleaseYear("2015");

        return List.of(mockFilm1, mockFilm2, mockFilm3);
    }

    @Test
    public void testWhenCreateNewMockList_ThenReturnList() {
        List<Film> createdListMock = createListOfFilms();

        Film mockFilm1 = new Film();
        mockFilm1.setId(mockUUID1);
        mockFilm1.setTitle("Prisoner of my Heart");
        mockFilm1.setLengthInMinutes(113);
        mockFilm1.setReleaseYear("2005");

        Film mockFilm2 = new Film();
        mockFilm2.setId(mockUUID2);
        mockFilm2.setTitle("Avenger: Finite War");
        mockFilm2.setLengthInMinutes(210);
        mockFilm2.setReleaseYear("2022");

        Film mockFilm3 = new Film();
        mockFilm3.setId(mockUUID3);
        mockFilm3.setTitle("Apfel: Steve Arbeit");
        mockFilm3.setLengthInMinutes(95);
        mockFilm3.setReleaseYear("2015");

        assertEquals(3, createdListMock.size());
        assertArrayEquals(List.of(mockFilm1, mockFilm2, mockFilm3).toArray(), createdListMock.toArray());
    }

    @Test
    public void testWhenAddAll() {
        List<Film> createdListMock = createListOfFilms();
        filmServiceMock.addFilms(createdListMock);

        verify(filmRepositoryMock, Mockito.times(1)).saveAll(createdListMock);
        assertEquals(3, mockRepository.size());
    }

    @Test
    public void testWhenGetAllFilms_ThenReturnAll() {
        List<Film> result = filmServiceMock.getAllFilms();

        verify(filmRepositoryMock, Mockito.times(1)).findAll();
        assertEquals(0, result.size());

        List<Film> createdListMock = createListOfFilms();
        filmServiceMock.addFilms(createdListMock);

        List<Film> resultWithFilms = filmServiceMock.getAllFilms();

        verify(filmRepositoryMock, Mockito.times(2)).findAll();
        assertEquals(3, resultWithFilms.size());
        assertArrayEquals(createdListMock.toArray(), resultWithFilms.toArray());
    }

    // Change
    @Test
    public void testWhenGetFilmById_ThenFlow() throws NoSuchFileException {
        List<Film> createdListMock = createListOfFilms();
        filmServiceMock.addFilms(createdListMock);

        Film result = filmServiceMock.getFilmById(mockUUID1);

        verify(filmRepositoryMock, Mockito.times(1)).findById(mockUUID1);
        assertEquals(createdListMock.get(0), result);
    }

    // exception name to result
    @Test
    public void testWhenGetFilmByIdButIDNotExist_ThenThrow() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> filmServiceMock.getFilmById(UUID.randomUUID()));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    public void testWhenAddFilm_ThenFlow() {
        Film filmMock = new Film();
        filmMock.setId(mockUUID1);
        filmMock.setTitle("Test: The Test");
        filmMock.setLengthInMinutes(123);
        filmMock.setReleaseYear("1234");

        Film result = filmServiceMock.addFilm(filmMock);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(filmMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(filmMock);
        assertEquals(filmMock, result);
        assertEquals(1, mockRepository.size());
    }

    @Test
    public void testWhenAddFilmAlreadyExist_ThenThrowError() {
        Film filmMock = new Film();
        filmMock.setId(mockUUID1);
        filmMock.setTitle("Test: The Test");
        filmMock.setLengthInMinutes(123);
        filmMock.setReleaseYear("1234");

        filmServiceMock.addFilm(filmMock);

        Exception exception = assertThrows(EntityExistsException.class, () -> filmServiceMock.addFilm(filmMock));
        String expected = "Film already exist";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(2)).existsById(filmMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(filmMock);
    }

    @Test
    public void testWhenUpdateFilm_ThenFlow() {
        List<Film> createdListMock = createListOfFilms();
        filmServiceMock.addFilms(createdListMock);

        Film mockEditedFilm = new Film(createdListMock.get(1));
        mockEditedFilm.setReleaseYear("1999");
        mockEditedFilm.setTitle("Avenger: NaN War");

        filmServiceMock.updateFilm(mockEditedFilm);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(mockEditedFilm.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(mockEditedFilm);

        assertNotEquals(createdListMock.get(1), mockEditedFilm);

        List<Film> allFilmResult = filmServiceMock.getAllFilms();

        assertEquals(mockEditedFilm, allFilmResult.get(1));
    }

    @Test
    public void testWhenUpdateFilmButIDNotExist_ThenThrowError() {
        Film filmMock = new Film();
        filmMock.setId(mockUUID1);
        filmMock.setTitle("Test: The Test");
        filmMock.setLengthInMinutes(123);
        filmMock.setReleaseYear("1234");

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> filmServiceMock.updateFilm(filmMock));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(filmMock.getId());
        verify(filmRepositoryMock, Mockito.times(0)).save(filmMock);
    }

    @Test
    public void testWhenDeleteFilm_ThenFlow() {
        List<Film> createdListMock = createListOfFilms();
        filmServiceMock.addFilms(createdListMock);

        Film toBeDeletedMock = createdListMock.get(2);

        filmServiceMock.deleteFilm(toBeDeletedMock.getId());

        verify(filmRepositoryMock, Mockito.times(1)).existsById(toBeDeletedMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).deleteById(toBeDeletedMock.getId());

        assertEquals(2, mockRepository.size());
        assertFalse(mockRepository.existsById(toBeDeletedMock.getId()));
    }

    @Test
    public void testWhenDeleteFilmButFilmNotExist_ThenThrow() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> filmServiceMock.deleteFilm(mockUUID1));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(mockUUID1);
        verify(filmRepositoryMock, Mockito.times(0)).deleteById(mockUUID1);
    }

}
