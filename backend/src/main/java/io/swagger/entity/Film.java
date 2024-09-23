package io.swagger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "films")
public class Film {

    @Id
    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "lengthInMinutes", nullable = false)
    private BigDecimal lengthInMinutes;

    @Column(name = "moodType", nullable = false, length = 255)
    private String moodType;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String moodType) {
        this.moodType = moodType;
    }
}
