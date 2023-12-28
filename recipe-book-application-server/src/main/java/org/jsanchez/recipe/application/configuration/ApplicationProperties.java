package org.jsanchez.recipe.application.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Properties;

/**
 * Class responsible to load the application properties
 */
@Singleton
@NoArgsConstructor
@Slf4j
public final class ApplicationProperties {

    public static final String SERVER_PORT_KEY = "server.port";
    public static final String PERSISTENCE_UNIT_KEY = "persistence.unit";
    private static final String DEFAULT_APPLICATION_PROPERTIES_FILE = "application.properties";

    @Getter
    private Properties properties;
    private String propertiesFile;

    public ApplicationProperties(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }


    /**
     * Load the provided properties file, otherwise the default application.properties file
     * @return the current instance of {@link ApplicationProperties}
     */
    public ApplicationProperties loadProperties() {
        initialise();
        load();
        validate();
        return this;
    }

    private void load() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(propertiesFile);
            if (inputStream == null) {
                log.error("Exception loading: {}, file not found", propertiesFile);
                throw new IllegalArgumentException("file " + propertiesFile + " not found!");
            } else {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            log.error("Exception loading properties from: {}", propertiesFile);
            throw new RuntimeException(e);
        }
    }

    /**
     * Fields Initialisation
     */
    private void initialise() {
        if (Objects.isNull(propertiesFile)) {
            propertiesFile = DEFAULT_APPLICATION_PROPERTIES_FILE;
        }
        if (Objects.isNull(properties)) {
            properties = new Properties();
        }
    }

    /**
     * Validation of mandatory properties
     */
    private void validate() {
        if (properties.isEmpty() || !properties.containsKey(SERVER_PORT_KEY) ||
                !properties.containsKey(PERSISTENCE_UNIT_KEY)) {
            log.error("Error validating the properties provided in the file {}", propertiesFile);
            throw new InputMismatchException(
                    "Error validating the properties provided in the file [" +
                            propertiesFile + ", the properties 'server.port' and 'persistence.unit' are mandatory");
        }
    }

    /**
     * To retrieve a property value by key as {@link String}
     * @param key property key
     * @return property value as {@link String}
     */
    public String getPropertyAsString(String key) {
        return (String) this.properties.get(key);
    }

    /**
     * To retrieve a property value by key as {@link Integer}
     * @param key property key
     * @return property value as {@link Integer}
     */
    public Integer getPropertyAsInt(String key) {
        return Integer.parseInt(getPropertyAsString(key));
    }

    /**
     * To retrieve a property value as {@link Object}
     * @param key property key
     * @return
     */
    public Object getPropertyByKey(String key) {
        return properties.get(key);
    }

}
