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

    public static final String EXPECTED_EXCEPTION_MESSAGE = "Film not found";
    @InjectMocks
    FilmService testee;

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
        //Arrange
        Film expectedFilm1 = new Film();
        expectedFilm1.setId(mockUUID1);
        expectedFilm1.setTitle("Prisoner of my Heart");
        expectedFilm1.setLengthInMinutes(113);
        expectedFilm1.setReleaseYear("2005");

        Film expectedFilm2 = new Film();
        expectedFilm2.setId(mockUUID2);
        expectedFilm2.setTitle("Avenger: Finite War");
        expectedFilm2.setLengthInMinutes(210);
        expectedFilm2.setReleaseYear("2022");

        Film expectedFilm3 = new Film();
        expectedFilm3.setId(mockUUID3);
        expectedFilm3.setTitle("Apfel: Steve Arbeit");
        expectedFilm3.setLengthInMinutes(95);
        expectedFilm3.setReleaseYear("2015");

        //Act
        List<Film> createdListMock = createListOfFilms();


        //Assert
        assertEquals(3, createdListMock.size());
        assertArrayEquals(List.of(expectedFilm1, expectedFilm2, expectedFilm3).toArray(), createdListMock.toArray());
    }

    @Test
    public void testWhenAddAll() {
        //Arrange
        List<Film> createdListMock = createListOfFilms();

        //Act
        testee.addFilms(createdListMock);

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).saveAll(createdListMock);
        assertEquals(3, mockRepository.size());
    }

    @Test
    public void testWhenGetAllFilmsIsEmpty_ThenReturnAll() {
        //Arrange //Act
        List<Film> filmsFromService = testee.getAllFilms();

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).findAll();
        assertEquals(0, filmsFromService.size());
    }

    @Test
    public void testWhenGetAllFilmsIsNotEmpty_ThenReturnAll() {
        //Arrange
        List<Film> createdListMock = createListOfFilms();
        testee.addFilms(createdListMock);

        //Act
        List<Film> resultWithFilms = testee.getAllFilms();

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).findAll();
        assertEquals(3, resultWithFilms.size());
        assertArrayEquals(createdListMock.toArray(), resultWithFilms.toArray());
    }

    // Change
    @Test
    public void testWhenGetFilmById_ThenFlow() throws NoSuchFileException {
        //Arrange
        List<Film> createdListMock = createListOfFilms();
        testee.addFilms(createdListMock);

        //Act
        Film result = testee.getFilmById(mockUUID1);

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).findById(mockUUID1);
        assertEquals(createdListMock.getFirst(), result);
    }

    // exception name to result
    @Test
    public void testWhenGetFilmByIdButIDNotExist_ThenThrow() {
        //Arrange //Act
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> testee.getFilmById(UUID.randomUUID()));
        String actualExceptionMessage = exception.getMessage();

        //Assert
        assertTrue(actualExceptionMessage.contains(EXPECTED_EXCEPTION_MESSAGE));
    }

    @Test
    public void testWhenAddFilm_ThenFlow() {
        //Arrange
        Film expectedFilm = new Film();
        expectedFilm.setId(mockUUID1);
        expectedFilm.setTitle("Test: The Test");
        expectedFilm.setLengthInMinutes(123);
        expectedFilm.setReleaseYear("1234");

        //Act
        Film actualFilm = testee.addFilm(expectedFilm);

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).existsById(expectedFilm.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(expectedFilm);
        assertEquals(expectedFilm, actualFilm);
        assertEquals(1, mockRepository.size());
    }

    @Test
    public void testWhenAddFilmAlreadyExist_ThenThrowError() {
        //Arrange
        Film expectedFilm = new Film();
        expectedFilm.setId(mockUUID1);
        expectedFilm.setTitle("Test: The Test");
        expectedFilm.setLengthInMinutes(123);
        expectedFilm.setReleaseYear("1234");


        //Act
        testee.addFilm(expectedFilm);


        //Assert
        Exception exception = assertThrows(EntityExistsException.class, () -> testee.addFilm(expectedFilm));
        String expected = "Film already exist";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(2)).existsById(expectedFilm.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(expectedFilm);
    }

    @Test
    public void testWhenUpdateFilm_ThenFlow() {
        //Arrange
        List<Film> createdListMock = createListOfFilms();
        testee.addFilms(createdListMock);

        Film mockEditedFilm = new Film(createdListMock.get(1));
        mockEditedFilm.setReleaseYear("1999");
        mockEditedFilm.setTitle("Avenger: NaN War");

        //Act
        testee.updateFilm(mockEditedFilm);

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).existsById(mockEditedFilm.getId());
        verify(filmRepositoryMock, Mockito.times(1)).save(mockEditedFilm);

        assertNotEquals(createdListMock.get(1), mockEditedFilm);

        List<Film> allFilmResult = testee.getAllFilms();

        assertEquals(mockEditedFilm, allFilmResult.get(1));
    }

    @Test
    public void testWhenUpdateFilmButIDNotExist_ThenThrowError() {
        //Arrange
        Film editFilm = new Film();
        editFilm.setId(mockUUID1);
        editFilm.setTitle("Test: The Test");
        editFilm.setLengthInMinutes(123);
        editFilm.setReleaseYear("1234");

        //Act //Assert
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> testee.updateFilm(editFilm));
        String actual = exception.getMessage();

        assertEquals(EXPECTED_EXCEPTION_MESSAGE, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(editFilm.getId());
        verify(filmRepositoryMock, Mockito.times(0)).save(editFilm);
    }

    @Test
    public void testWhenDeleteFilm_ThenFlow() {
        //Arrange
        List<Film> createdListMock = createListOfFilms();
        testee.addFilms(createdListMock);

        Film toBeDeletedMock = createdListMock.get(2);

        //Act
        testee.deleteFilm(toBeDeletedMock.getId());

        //Assert
        verify(filmRepositoryMock, Mockito.times(1)).existsById(toBeDeletedMock.getId());
        verify(filmRepositoryMock, Mockito.times(1)).deleteById(toBeDeletedMock.getId());

        assertEquals(2, mockRepository.size());
        assertFalse(mockRepository.existsById(toBeDeletedMock.getId()));
    }

    @Test
    public void testWhenDeleteFilmButFilmNotExist_ThenThrow() {
        //Arrange //Act //Assert
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> testee.deleteFilm(mockUUID1));
        String expected = "Film not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(filmRepositoryMock, Mockito.times(1)).existsById(mockUUID1);
        verify(filmRepositoryMock, Mockito.times(0)).deleteById(mockUUID1);
    }

}
