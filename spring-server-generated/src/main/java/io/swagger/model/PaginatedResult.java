package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.configuration.NotUndefined;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PaginatedResult
 */
@Validated
@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-08-31T15:00:15.128234763Z[GMT]")


public class PaginatedResult   {
  @JsonProperty("offset")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer offset = null;

  @JsonProperty("limit")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer limit = null;

  @JsonProperty("total")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer total = null;

  @JsonProperty("data")
  @Valid
  private List<Object> data = null;

  public PaginatedResult offset(Integer offset) { 

    this.offset = offset;
    return this;
  }

  /**
   * The page offset
   * @return offset
   **/
  
  @Schema(example = "10", description = "The page offset")
  
  public Integer getOffset() {  
    return offset;
  }



  public void setOffset(Integer offset) { 
    this.offset = offset;
  }

  public PaginatedResult limit(Integer limit) { 

    this.limit = limit;
    return this;
  }

  /**
   * The page limit
   * @return limit
   **/
  
  @Schema(example = "5", description = "The page limit")
  
  public Integer getLimit() {  
    return limit;
  }



  public void setLimit(Integer limit) { 
    this.limit = limit;
  }

  public PaginatedResult total(Integer total) { 

    this.total = total;
    return this;
  }

  /**
   * The total number of items
   * @return total
   **/
  
  @Schema(example = "420", description = "The total number of items")
  
  public Integer getTotal() {  
    return total;
  }



  public void setTotal(Integer total) { 
    this.total = total;
  }

  public PaginatedResult data(List<Object> data) { 

    this.data = data;
    return this;
  }

  public PaginatedResult addDataItem(Object dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<Object>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  
  @Schema(description = "")
  
  public List<Object> getData() {  
    return data;
  }



  public void setData(List<Object> data) { 
    this.data = data;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaginatedResult paginatedResult = (PaginatedResult) o;
    return Objects.equals(this.offset, paginatedResult.offset) &&
        Objects.equals(this.limit, paginatedResult.limit) &&
        Objects.equals(this.total, paginatedResult.total) &&
        Objects.equals(this.data, paginatedResult.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, limit, total, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginatedResult {\n");
    
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
