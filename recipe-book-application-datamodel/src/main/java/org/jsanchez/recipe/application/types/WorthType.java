package org.jsanchez.recipe.application.types;

import java.util.stream.Stream;

/**
 * Worth type for a set of worthy values
 */
public enum WorthType {
    YEAH("yeah"),
    MEH("meh"),
    NAH("nah"),
    INVALID("INVALID");

    private final String value;

    WorthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public static WorthType fromValue(String value) {
        return Stream.of(values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
