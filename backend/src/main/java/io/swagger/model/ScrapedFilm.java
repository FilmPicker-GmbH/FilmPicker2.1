package io.swagger.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "wikiUrl",
        "poster",
        "name",
        "directedBy",
        "writtenBy",
        "storyBy",
        "producedBy",
        "cinematography",
        "editedBy",
        "releaseDate",
        "runningTime",
        "country",
        "language",
        "budget",
        "boxOffice",
        "casts"
})
@ToString
public class ScrapedFilm {

    @JsonProperty("wikiUrl")
    private String wikiUrl;
    @JsonProperty("poster")
    private String poster;
    @JsonProperty("name")
    private String name;
    @JsonProperty("directedBy")
    private List<String> directedBy;
    @JsonProperty("writtenBy")
    private List<String> writtenBy;
    @JsonProperty("storyBy")
    private List<String> storyBy;
    @JsonProperty("producedBy")
    private List<String> producedBy;
    @JsonProperty("cinematography")
    private List<String> cinematography;
    @JsonProperty("editedBy")
    private List<String> editedBy;
    @JsonProperty("releaseDate")
    private String releaseDate;
    @JsonProperty("runningTime")
    private String runningTime;
    @JsonProperty("country")
    private List<String> country;
    @JsonProperty("language")
    private List<String> language;
    @JsonProperty("budget")
    private String budget;
    @JsonProperty("boxOffice")
    private String boxOffice;
    @JsonProperty("casts")
    private List<Cast> casts;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("wikiUrl")
    public String getWikiUrl() {
        return wikiUrl;
    }

    @JsonProperty("wikiUrl")
    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    @JsonProperty("poster")
    public String getPoster() {
        return poster;
    }

    @JsonProperty("poster")
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("directedBy")
    public List<String> getDirectedBy() {
        return directedBy;
    }

    @JsonProperty("directedBy")
    public void setDirectedBy(List<String> directedBy) {
        this.directedBy = directedBy;
    }

    @JsonProperty("writtenBy")
    public List<String> getWrittenBy() {
        return writtenBy;
    }

    @JsonProperty("writtenBy")
    public void setWrittenBy(List<String> writtenBy) {
        this.writtenBy = writtenBy;
    }

    @JsonProperty("storyBy")
    public List<String> getStoryBy() {
        return storyBy;
    }

    @JsonProperty("storyBy")
    public void setStoryBy(List<String> storyBy) {
        this.storyBy = storyBy;
    }

    @JsonProperty("producedBy")
    public List<String> getProducedBy() {
        return producedBy;
    }

    @JsonProperty("producedBy")
    public void setProducedBy(List<String> producedBy) {
        this.producedBy = producedBy;
    }

    @JsonProperty("cinematography")
    public List<String> getCinematography() {
        return cinematography;
    }

    @JsonProperty("cinematography")
    public void setCinematography(List<String> cinematography) {
        this.cinematography = cinematography;
    }

    @JsonProperty("editedBy")
    public List<String> getEditedBy() {
        return editedBy;
    }

    @JsonProperty("editedBy")
    public void setEditedBy(List<String> editedBy) {
        this.editedBy = editedBy;
    }

    @JsonProperty("releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("releaseDate")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @JsonProperty("runningTime")
    public String getRunningTime() {
        return runningTime;
    }

    @JsonProperty("runningTime")
    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    @JsonProperty("country")
    public List<String> getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(List<String> country) {
        this.country = country;
    }

    @JsonProperty("language")
    public List<String> getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(List<String> language) {
        this.language = language;
    }

    @JsonProperty("budget")
    public String getBudget() {
        return budget;
    }

    @JsonProperty("budget")
    public void setBudget(String budget) {
        this.budget = budget;
    }

    @JsonProperty("boxOffice")
    public String getBoxOffice() {
        return boxOffice;
    }

    @JsonProperty("boxOffice")
    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    @JsonProperty("casts")
    public List<Cast> getCasts() {
        return casts;
    }

    @JsonProperty("casts")
    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}