package org.jsanchez.recipe.application.types;

import java.util.stream.Stream;

/**
 * Meal type for a set of meal values
 */
public enum MealType {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private final String value;

    MealType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public static MealType fromValue(String value) {
        return Stream.of(values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
