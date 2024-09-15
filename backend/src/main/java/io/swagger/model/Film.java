package io.swagger.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.configuration.NotUndefined;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Film
 */
@Validated
@NotUndefined
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-08-31T15:00:15.128234763Z[GMT]")


public class Film   {
  @JsonProperty("id")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String id = null;

  @JsonProperty("title")

  private String title = null;

  @JsonProperty("length")

  private BigDecimal length = null;

  /**
   * The film mood type
   */
  public enum MoodTypeEnum {
    LIGHT("LIGHT"),
    
    HEAVY("HEAVY"),
    
    WHOLESOME("WHOLESOME"),
    
    EXCITING("EXCITING");

    private String value;

    MoodTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MoodTypeEnum fromValue(String text) {
      for (MoodTypeEnum b : MoodTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("moodType")

  private MoodTypeEnum moodType = null;


  public Film id(String id) { 

    this.id = id;
    return this;
  }

  /**
   * The film ID
   * @return id
   **/
  
  @Schema(example = "42", accessMode = Schema.AccessMode.READ_ONLY, description = "The film ID")
  
  public String getId() {  
    return id;
  }



  public void setId(String id) { 
    this.id = id;
  }

  public Film title(String title) { 

    this.title = title;
    return this;
  }

  /**
   * The film title
   * @return title
   **/
  
  @Schema(example = "The Shawshank Redemption", required = true, description = "The film title")
  
  @NotNull
  public String getTitle() {  
    return title;
  }



  public void setTitle(String title) { 

    this.title = title;
  }

  public Film length(BigDecimal length) { 

    this.length = length;
    return this;
  }

  /**
   * The film length in minutes
   * @return length
   **/
  
  @Schema(example = "142", required = true, description = "The film length in minutes")
  
@Valid
  @NotNull
  public BigDecimal getLength() {  
    return length;
  }



  public void setLength(BigDecimal length) { 

    this.length = length;
  }

  public Film moodType(MoodTypeEnum moodType) { 

    this.moodType = moodType;
    return this;
  }

  /**
   * The film mood type
   * @return moodType
   **/
  
  @Schema(example = "LIGHT, WHOLESOME", required = true, description = "The film mood type")
  
  @NotNull
  public MoodTypeEnum getMoodType() {  
    return moodType;
  }



  public void setMoodType(MoodTypeEnum moodType) {
    this.moodType = moodType;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Film film = (Film) o;
    return Objects.equals(this.id, film.id) &&
        Objects.equals(this.title, film.title) &&
        Objects.equals(this.length, film.length) &&
        Objects.equals(this.moodType, film.moodType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, length, moodType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Film {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    moodType: ").append(toIndentedString(moodType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
