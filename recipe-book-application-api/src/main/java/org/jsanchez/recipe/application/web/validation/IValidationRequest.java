package org.jsanchez.recipe.application.web.validation;

import org.jsanchez.recipe.application.error.ApplicationException;
import spark.Request;

import java.util.Arrays;
import java.util.Objects;

/**
 * Interface to validate requests
 */
public interface IValidationRequest {
    void hasExpectedQueryParamsOtherwiseException(Request request, String... paramsExpected) throws ApplicationException;

    void hasValidBodyOtherwiseException(Request request) throws ApplicationException;

    default boolean hasValidBody(Request request) {
        return (Objects.nonNull(request.body()) && !request.body().isBlank() && !request.body().isEmpty());
    }

    default boolean hasQueryParams(Request request, String... paramsExpected) {
        return Arrays.stream(paramsExpected).anyMatch(request.queryParams()::contains);
    }
}
