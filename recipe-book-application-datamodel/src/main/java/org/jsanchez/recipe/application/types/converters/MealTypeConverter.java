package org.jsanchez.recipe.application.types.converters;

import org.jsanchez.recipe.application.types.MealType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * Attribute converter for {@link MealType}
 */
@Converter
public class MealTypeConverter implements AttributeConverter<MealType, String> {

    @Override
    public String convertToDatabaseColumn(MealType attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public MealType convertToEntityAttribute(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return MealType.fromValue(value);
    }
}
