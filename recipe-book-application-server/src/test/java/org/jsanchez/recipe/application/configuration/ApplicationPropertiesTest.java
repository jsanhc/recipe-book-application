package org.jsanchez.recipe.application.configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

/**
 * Unit test for {@link ApplicationProperties}
 */
public class ApplicationPropertiesTest {

    private static final String PROP_FILE = "application-test.properties";
    private static final String PROP_FILE_EMPTY = "application-empty.properties";

    @Test
    void shouldLoadProperties() {
        // given
        Injector injector = Guice.createInjector(
                new ApplicationGuiceModule(new ApplicationProperties(PROP_FILE).loadProperties())
        );
        ApplicationProperties applicationPropertiesExpected = new ApplicationProperties(PROP_FILE).loadProperties();

        // when
        ApplicationProperties applicationProperties = injector.getInstance(ApplicationProperties.class);

        // then
        assertThat(applicationProperties.getProperties().keySet()).containsExactlyInAnyOrderElementsOf(
                applicationPropertiesExpected.getProperties().keySet()
        );
        assertThat(applicationProperties.getProperties().values()).containsExactlyInAnyOrderElementsOf(
                applicationPropertiesExpected.getProperties().values()
        );
    }

    @Test
    void shouldHaveMandatoryProperties() {
        // given
        Injector injector = Guice.createInjector(
                new ApplicationGuiceModule(new ApplicationProperties(PROP_FILE).loadProperties())
        );

        // when
        ApplicationProperties applicationProperties = injector.getInstance(ApplicationProperties.class);

        // then
        assertThat(applicationProperties.getProperties()).containsKeys(
                "server.port", "persistence.unit"
        );
    }

    @Test
    void shouldThrowErrorMandatoryProperties() {
        // when
        InputMismatchException exception = catchThrowableOfType(() -> Guice.createInjector(
                        new ApplicationGuiceModule(new ApplicationProperties(PROP_FILE_EMPTY).loadProperties())
                ), InputMismatchException.class
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Error validating the properties provided in the file [" +
                PROP_FILE_EMPTY + ", the properties 'server.port' and 'persistence.unit' are mandatory");
    }

    @Test
    void shouldThrowAndErrorLoadingPropertiesFile() {
        // when
        IllegalArgumentException exception = catchThrowableOfType(() -> Guice.createInjector(
                        new ApplicationGuiceModule(new ApplicationProperties("unknownFile.properties").loadProperties())
                ), IllegalArgumentException.class
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("file unknownFile.properties not found!");
    }
}
