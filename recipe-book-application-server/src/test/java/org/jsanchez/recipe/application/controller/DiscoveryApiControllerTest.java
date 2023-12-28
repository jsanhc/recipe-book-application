package org.jsanchez.recipe.application.controller;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.http.ApiTestUtils;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.controller.DiscoveryApiController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.inject.Guice.createInjector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_DISCOVERY_CONTROLLER;
import static org.jsanchez.recipe.application.web.controller.DiscoveryApiController.ROOT_CONTEXT_PATH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for {@link DiscoveryApiController}
 */
public class DiscoveryApiControllerTest {

    public final String SERVER_PORT = "4567";
    private final String SERVER_HOST = "http://localhost:" + SERVER_PORT;
    private static ApplicationApi discoveryController;

    @BeforeAll
    public static void setup() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();
        Injector injector = createInjector(new ApplicationGuiceModule(applicationProperties));
        Map<String, ApplicationApi> mapControllers = injector.getInstance(
                Key.get(new TypeLiteral<Map<String, ApplicationApi>>() {
                })
        );
        discoveryController = mapControllers.get(KEY_DISCOVERY_CONTROLLER);
        assertDoesNotThrow(() -> discoveryController.initialize());
        spark.Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        spark.Spark.stop();
    }

    @Test
    void shouldHaveOperations() {
        List<RestOperation> operations = ((DiscoveryApiController) discoveryController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).hasSize(1);
    }

    private static Stream<Arguments> validArguments() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH,
                        Method.GET.name()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void shouldValidateOperationsController(String contextPath, String method) {
        List<RestOperation> operations = ((DiscoveryApiController) discoveryController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).filteredOn(
                operation -> operation.getPath().equals(contextPath) && operation.getOperation().equals(method)
        );
    }

    private static Stream<Arguments> aValidGetRequests() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH,
                        Method.GET.name()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("aValidGetRequests")
    void shouldRequestGetEndpoints(String contextPath, String method) {
        ApiTestUtils apiTestUtils = ApiTestUtils.builder().method(method).host(SERVER_HOST).path(contextPath)
                .responseType(ApplicationResponse.class).build();
        // when
        ApiTestUtils.TestResponse<ApplicationResponse> res = apiTestUtils.request();
        // then
        assertThat(res.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(res.getBody().getCode()).isEqualTo(ApplicationResponseCodes.SUCCESS);
    }
}
