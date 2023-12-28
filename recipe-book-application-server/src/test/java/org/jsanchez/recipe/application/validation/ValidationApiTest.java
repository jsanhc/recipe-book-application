package org.jsanchez.recipe.application.validation;

import org.jsanchez.recipe.application.ServerBaseTest;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.web.validation.IValidationRequest;
import org.jsanchez.recipe.application.web.validation.ValidationRequest;
import org.junit.jupiter.api.Test;
import spark.Request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link ValidationRequest}
 */
public class ValidationApiTest extends ServerBaseTest {

    @Test
    void shouldThrowExceptionQueryParameters() {
        // given
        IValidationRequest validationApi = injector.getInstance(IValidationRequest.class);
        Request request = mock(Request.class);
        // when
        ApplicationException exception = catchThrowableOfType(() ->
                        validationApi.hasExpectedQueryParamsOtherwiseException(request, "paramName")
                , ApplicationException.class
        );
        // then
        assertThat(exception.getErrorCodeType()).isEqualTo(ApplicationErrorType.QUERY_PARAMETERS_NOT_SUPPORTED);
    }

    @Test
    void shouldThrowExceptionEmptyBody() {
        // given
        IValidationRequest validationApi = injector.getInstance(IValidationRequest.class);
        Request request = mock(Request.class);
        // when
        ApplicationException exception = catchThrowableOfType(() ->
                        validationApi.hasValidBodyOtherwiseException(request)
                , ApplicationException.class
        );
        // then
        assertThat(exception.getErrorCodeType()).isEqualTo(ApplicationErrorType.EMPTY_BODY_NOT_SUPPORTED);
    }

    @Test
    void shouldBeFalse() {
        // given
        IValidationRequest validationApi = injector.getInstance(IValidationRequest.class);
        Request request = mock(Request.class);
        // when / then
        assertThat(validationApi.hasQueryParams(request)).isFalse();
    }
}
