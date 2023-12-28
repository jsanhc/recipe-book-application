package org.jsanchez.recipe.application.web.api;

/**
 * Interface to implement a controller to intercepts the requests <br>
 * The controller needs to be registered in the application guice module to be injected and initialized.
 */
public interface ApplicationApi {
    void initialize();
}
