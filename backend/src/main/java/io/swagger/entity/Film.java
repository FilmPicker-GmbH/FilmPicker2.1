package io.swagger.entity;

import java.util.UUID;

import io.swagger.converter.MoodTypeConverter;
import io.swagger.model.ScrappedFilm;
import io.swagger.types.MoodType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "films")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Film extends BaseEntity<UUID> {

    public Film(Film toBeCopy) {
        setId(toBeCopy.getId());
        this.title = toBeCopy.getTitle();
        this.lengthInMinutes = toBeCopy.getLengthInMinutes();
        this.moodType = toBeCopy.getMoodType();
    }

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "lengthInMinutes", nullable = false)
    private int lengthInMinutes;

    /*
     * @ElementCollection
     * 
     * @Column(name="director", nullable = false)
     * private List<String> directors;
     */

    @Column(name = "releaseYear", nullable = true)
    private String releaseYear;

    @Convert(converter = MoodTypeConverter.class)
    @Column(name = "moodType", nullable = true, length = 255)
    private MoodType moodType;

    /*
     * @ManyToMany
     * 
     * @Column(name = "casts", nullable = false)
     * private List<Cast> casts;
     */
    public Film(ScrappedFilm film) {
        this.setId(UUID.randomUUID());
        this.title = film.getName();
        this.lengthInMinutes = Integer.parseInt(film.getRunningTime());
        // this.directors = film.getDirectedBy();
        this.releaseYear = film.getReleaseDate();
        // this.casts = film.getCasts();
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public MoodType getMoodType() {
        return moodType;
    }

    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
}
