
package io.swagger;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.mockito.Mockito;

import io.swagger.entity.BaseEntity;
import io.swagger.entity.Film;
import io.swagger.mockrepository.MockRepository;
import io.swagger.repository.FilmRepository;

public class MockHelper {
    public static <T extends BaseEntity<UUID>> void setupMockRepository(FilmRepository filmRepository,
            MockRepository<T, UUID> mockRepository) {

        lenient().when(filmRepository.findAll())
                .thenAnswer(invocation -> mockRepository.findAll());

        lenient().doAnswer(invocation -> {
            List<T> films = invocation.getArgument(0);
            System.out.println("INVOCATION : " + invocation);
            mockRepository.saveAll(films);
            return null;
        }).when(filmRepository).saveAll(Mockito.<Film>anyList());

        lenient().when(filmRepository.findById(Mockito.any(UUID.class)))
                .thenAnswer(invocation -> {
                    UUID uuid = invocation.getArgument(0);
                    return mockRepository.findById(uuid);
                });

        lenient().doAnswer(invocation -> {
            UUID uuid = invocation.getArgument(0);
            mockRepository.deleteById(uuid);
            return null;
        }).when(filmRepository).deleteById(Mockito.any(UUID.class));

        lenient().when(filmRepository.existsById(Mockito.any(UUID.class)))
                .thenAnswer(invocation -> {
                    UUID uuid = invocation.getArgument(0);
                    return mockRepository.existsById(uuid);
                });

        lenient().when(filmRepository.save(Mockito.any(Film.class)))
                .thenAnswer(invocation -> {
                    T toBeAddedFilm = invocation.getArgument(0);
                    return mockRepository.save(toBeAddedFilm);
                });
    }
}
