package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.configuration.NotUndefined;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Error
 */
@Validated
@NotUndefined
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-08-31T15:00:15.128234763Z[GMT]")


public class Error   {
  @JsonProperty("message")

  private String message = null;


  public Error message(String message) { 

    this.message = message;
    return this;
  }

  /**
   * The error message
   * @return message
   **/
  
  @Schema(example = "Film not found", required = true, description = "The error message")
  
  @NotNull
  public String getMessage() {  
    return message;
  }



  public void setMessage(String message) { 

    this.message = message;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.message, error.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
