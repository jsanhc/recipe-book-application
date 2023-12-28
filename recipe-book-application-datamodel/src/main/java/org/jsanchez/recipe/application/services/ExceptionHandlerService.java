package org.jsanchez.recipe.application.services;

import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.handler.ExceptionHandler;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.springframework.orm.jpa.JpaSystemException;

/**
 * Implementation of {@link ExceptionHandler}
 */
public class ExceptionHandlerService implements ExceptionHandler {
    @Override
    public ApplicationException exceptionHandler(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof JpaSystemException) {
            message = ((JpaSystemException) exception).getRootCause().getMessage();
        }
        return new ApplicationException(exception, ApplicationErrorType.DATABASE_OPERATION_ERROR, message);
    }
}
