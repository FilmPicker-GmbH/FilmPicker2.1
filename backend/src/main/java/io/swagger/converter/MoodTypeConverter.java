package io.swagger.converter;

import io.swagger.types.MoodType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MoodTypeConverter implements AttributeConverter<MoodType, String> {

    @Override
    public String convertToDatabaseColumn(MoodType attribute) {
        return (attribute == null) ? null : attribute.toString();
    }

    @Override
    public MoodType convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : MoodType.fromValue(dbData);
    }
    
}
