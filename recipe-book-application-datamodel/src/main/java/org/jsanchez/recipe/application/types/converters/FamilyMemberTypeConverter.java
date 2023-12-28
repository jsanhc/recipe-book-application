package org.jsanchez.recipe.application.types.converters;

import org.jsanchez.recipe.application.types.FamilyMemberType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * Attribute converter implementation to convert {@link FamilyMemberType}
 */
@Converter
public class FamilyMemberTypeConverter implements AttributeConverter<FamilyMemberType, String> {
    @Override
    public String convertToDatabaseColumn(FamilyMemberType attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public FamilyMemberType convertToEntityAttribute(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return FamilyMemberType.fromValue(value);
    }
}
