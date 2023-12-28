package org.jsanchez.recipe.application.web.server;

import lombok.extern.slf4j.Slf4j;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.web.api.ApplicationApi;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

import static org.jsanchez.recipe.application.configuration.ApplicationProperties.SERVER_PORT_KEY;
import static spark.Spark.port;

/**
 * Bootstrap application:<br>
 * <ul>
 *     <li>Initialise the server</li>
 *     <li>Initialise the controllers</li>
 * </ul>
 */
@Slf4j
public final class BootstrapApplicationServer {

    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    private Map<String, ApplicationApi> mapControllers;

    public void run() {
        int port = applicationProperties.getPropertyAsInt(SERVER_PORT_KEY);
        port(port);
        loadMapControllers();
        log.info("Server starter on http://localhost:{}", port);
    }

    private void loadMapControllers() {
        mapControllers.forEach((key,controllerInstance) -> {
            log.info("Initializing the controller {}", key);
            controllerInstance.initialize();
        });
    }
}
