package org.jsanchez.recipe.application.error.types;

import lombok.Getter;
import org.apache.hc.core5.http.HttpStatus;

import java.util.stream.Stream;

/**
 * Application Response codes
 */
@Getter
public enum ApplicationResponseCodes {
    SUCCESS(HttpStatus.SC_OK, "Success"),
    ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error");

    private int status;
    private String message;

    ApplicationResponseCodes(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ApplicationResponseCodes(String message) {
        this.status = HttpStatus.SC_OK;
        this.message = message;
    }

    public static ApplicationResponseCodes fromStatus(int status) {
        return Stream.of(values())
                .filter(type -> type.getStatus() == status)
                .findFirst()
                .orElse(null);
    }
}
