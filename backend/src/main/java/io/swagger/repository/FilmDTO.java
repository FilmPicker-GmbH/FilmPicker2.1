package io.swagger.repository;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.types.MoodType;

public record FilmDTO(
        UUID id,
        String title,
        BigDecimal lengthInMinutes,
        MoodType mood,
        String releaseYear) {
}