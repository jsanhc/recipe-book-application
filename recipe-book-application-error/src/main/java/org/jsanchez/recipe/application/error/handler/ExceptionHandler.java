package org.jsanchez.recipe.application.error.handler;

import org.jsanchez.recipe.application.error.ApplicationException;

/**
 * To manage the exceptions raised by the application
 */
public interface ExceptionHandler {
    ApplicationException exceptionHandler(Exception exception);
}
