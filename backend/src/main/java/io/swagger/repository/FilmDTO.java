package io.swagger.repository;

import io.swagger.model.Film;

import java.math.BigDecimal;

public record FilmDTO(
        String id,
        String title,
        BigDecimal length,
        Film.MoodTypeEnum mood) {
}