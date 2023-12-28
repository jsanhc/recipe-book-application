package org.jsanchez.recipe.application.web.validation;

import org.apache.hc.core5.http.HttpStatus;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import spark.Request;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Objects;

/**
 * Util to validate the client requests<br>
 * @see IValidationRequest
 */
@Singleton
public final class ValidationRequest implements IValidationRequest {

    @Override
    public void hasExpectedQueryParamsOtherwiseException(Request request, String... paramsExpected) throws ApplicationException {
        if (!Arrays.stream(paramsExpected).anyMatch(request.queryParams()::contains)) {
            throw new ApplicationException(
                    HttpStatus.SC_BAD_REQUEST,
                    ApplicationErrorType.QUERY_PARAMETERS_NOT_SUPPORTED,
                    String.format("Error parsing query parameters %s: " +
                                    "query parameters accepted: name(value: alphanumeric values)(mandatory) and sort(value: asc | desc)(optional)",
                            request.queryParams())
            );
        }
    }

    @Override
    public void hasValidBodyOtherwiseException(Request request) throws ApplicationException {
        if (!(Objects.nonNull(request.body()) && !request.body().isBlank() && !request.body().isEmpty())) {
            throw new ApplicationException(
                    HttpStatus.SC_BAD_REQUEST,
                    ApplicationErrorType.EMPTY_BODY_NOT_SUPPORTED,
                    String.format("Empty body not supported for this operation.")
            );
        }
    }
}
