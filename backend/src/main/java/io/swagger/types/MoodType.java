package io.swagger.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MoodType {
    LIGHT("LIGHT"),
    
    HEAVY("HEAVY"),
    
    WHOLESOME("WHOLESOME"),
    
    EXCITING("EXCITING");

    private String value;

    MoodType(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MoodType fromValue(String text) {
      for (MoodType b : MoodType.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }