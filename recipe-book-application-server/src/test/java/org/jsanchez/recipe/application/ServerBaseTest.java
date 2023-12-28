package org.jsanchez.recipe.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static com.google.inject.Guice.createInjector;

public class ServerBaseTest {
    public static final String PROP_FILE = "application-test.properties";
    public static Injector injector;
    public static Gson gson;

    public static TypeLiteral<Map<String, ApplicationApi>> MAP_OF_STRING_APPLICATION_API =
            new TypeLiteral<>() {
            };

    @BeforeAll
    public static void setup() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();
        gson = new GsonBuilder().setExclusionStrategies().excludeFieldsWithoutExposeAnnotation().create();
        injector = createInjector(new ApplicationGuiceModule(applicationProperties));
    }
}
