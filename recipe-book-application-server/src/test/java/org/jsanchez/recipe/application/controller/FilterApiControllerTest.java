package org.jsanchez.recipe.application.controller;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.controller.FilterApiController;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.inject.Guice.createInjector;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_FILTER_CONTROLLER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit Test for {@link FilterApiController}
 */
public class FilterApiControllerTest {

    @Test
    void shouldInitializeFilters() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();
        Injector injector = createInjector(new ApplicationGuiceModule(applicationProperties));
        Map<String, ApplicationApi> mapControllers = injector.getInstance(
                Key.get(new TypeLiteral<Map<String, ApplicationApi>>() {
                })
        );
        assertDoesNotThrow(() -> mapControllers.get(KEY_FILTER_CONTROLLER).initialize());
    }
}
