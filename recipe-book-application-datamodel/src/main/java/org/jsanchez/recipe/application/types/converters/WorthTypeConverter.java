package org.jsanchez.recipe.application.types.converters;

import org.jsanchez.recipe.application.types.WorthType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * Attribute converter for {@link WorthType}
 */
@Converter
public class WorthTypeConverter implements AttributeConverter<WorthType, String> {
    @Override
    public String convertToDatabaseColumn(WorthType attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public WorthType convertToEntityAttribute(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return WorthType.fromValue(value);
    }
}
