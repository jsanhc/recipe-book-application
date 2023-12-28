package org.jsanchez.recipe.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.web.server.BootstrapApplicationServer;

/**
 * Application entry point<br>
 * <ul>
 *     <li>Guice framework entry point: Creates the injector and initialise the {@link ApplicationGuiceModule}</li>
 *     <li>Initialize the server application {@link BootstrapApplicationServer}</li>
 * </ul>
 * @see <a href="http://localhost:8080">http://localhost:8080</a>
 */
@Slf4j
final class ApplicationEntryPoint {
    /**
     * Entry point
     * @param args command arguments (ignored)
     */
    public static void main(String[] args) {
        // loading the application properties
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();

        Injector injector = Guice.createInjector(new ApplicationGuiceModule(applicationProperties));
        // start the server
        BootstrapApplicationServer server = injector.getInstance(BootstrapApplicationServer.class);
        server.run();
    }
}
