package org.jsanchez.recipe.application.types;

import java.util.stream.Stream;

/**
 * Family member type for a set of family member values
 */
public enum FamilyMemberType {
    GRANDSON("GRANDSON"),
    GRANDDAUGHTER("GRANDDAUGHTER"),
    DAUGHTER("DAUGHTER"),
    HUSBAND("HUSBAND"),
    SON("SON"),
    BROTHER("BROTHER"),
    SISTER("SISTER"),
    ALL("ALL");

    private final String value;

    FamilyMemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public static FamilyMemberType fromValue(String value) {
        return Stream.of(values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
