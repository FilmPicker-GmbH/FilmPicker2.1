package io.swagger.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.swagger.entity.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, UUID> {

}
