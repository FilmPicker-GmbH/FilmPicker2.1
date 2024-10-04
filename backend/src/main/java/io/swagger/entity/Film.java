package io.swagger.entity;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.converter.MoodTypeConverter;
import io.swagger.model.ScrappedFilm;
import io.swagger.service.JsonToPojoService;
import io.swagger.types.MoodType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "films")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @Column(name = "id", nullable = false, length = 255)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "lengthInMinutes", nullable = false)
    private BigDecimal lengthInMinutes;

    /* 
    @ElementCollection
    @Column(name="director", nullable = false)
    private List<String> directors;
    */

    @Column(name="releaseYear", nullable = true)
    private String releaseYear;

    @Convert(converter = MoodTypeConverter.class)
    @Column(name = "moodType", nullable = true, length = 255)
    private MoodType moodType;

   /*  @ManyToMany
    @Column(name = "casts", nullable = false)
    private List<Cast> casts;
*/
    public Film(ScrappedFilm film) {
        this.id = UUID.randomUUID();
        this.title = film.getName();
        this.lengthInMinutes = BigDecimal.valueOf(JsonToPojoService.convertTimeToMinute(film.getRunningTime()));
        //this.directors = film.getDirectedBy();
        this.releaseYear = film.getReleaseDates();
        //this.casts = film.getCasts();
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(BigDecimal lengthInMinutes) {
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
