package io.swagger.repository;

import java.util.UUID;

import io.swagger.types.MoodType;

public record FilmDTO(
        UUID id,
        String title,
        int lengthInMinutes,
        MoodType mood,
        String releaseYear) {
}