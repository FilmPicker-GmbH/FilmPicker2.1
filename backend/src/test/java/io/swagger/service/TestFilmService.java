package io.swagger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.swagger.repository.FilmRepository;

@ExtendWith(MockitoExtension.class)
public class TestFilmService {
    
    @InjectMocks
    FilmService filmService;

    @Mock
    FilmRepository filmRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
}
