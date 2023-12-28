package org.jsanchez.recipe.application.controller;

import com.google.gson.Gson;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.http.ApiTestUtils;
import org.jsanchez.recipe.application.model.Ingredient;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.controller.IngredientApiController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.google.inject.Guice.createInjector;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_IMPLEMENTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_FILTER_CONTROLLER;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_INGREDIENT_CONTROLLER;
import static org.jsanchez.recipe.application.web.controller.IngredientApiController.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for {@link IngredientApiController}
 */
public class IngredientApiControllerTest {
    public final String SERVER_PORT = "4567";
    private final String SERVER_HOST = "http://localhost:" + SERVER_PORT;
    private static ApplicationApi ingredientController;
    private static Gson gson;

    @BeforeAll
    public static void setup() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();
        Injector injector = createInjector(new ApplicationGuiceModule(applicationProperties));
        gson = injector.getInstance(Gson.class);
        Map<String, ApplicationApi> mapControllers = injector.getInstance(
                Key.get(new TypeLiteral<Map<String, ApplicationApi>>() {
                })
        );
        ingredientController = mapControllers.get(KEY_INGREDIENT_CONTROLLER);
        assertDoesNotThrow(() -> ingredientController.initialize());
        assertDoesNotThrow(() -> mapControllers.get(KEY_FILTER_CONTROLLER).initialize());
        spark.Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        spark.Spark.stop();
    }

    @Test
    void shouldHaveOperations() {
        List<RestOperation> operations = ((IngredientApiController) ingredientController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).hasSize(6);
    }

    private static Stream<Arguments> validArguments() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH + ALL_CONTEXT_PATH,
                        Method.GET.name()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + FILTER_CONTEXT_PATH,
                        Method.GET.name()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + ADD_CONTEXT_PATH,
                        Method.POST.name()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + UPDATE_BY_ID_CONTEXT_PATH,
                        Method.POST.name()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + DELETE_BY_ID_CONTEXT_PATH,
                        Method.DELETE.name()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + DELETE_ALL_CONTEXT_PATH,
                        Method.DELETE.name()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void shouldValidateOperationsController(String contextPath, String method) {
        List<RestOperation> operations = ((IngredientApiController) ingredientController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).filteredOn(
                operation -> operation.getPath().equals(contextPath) && operation.getOperation().equals(method)
        );
    }

    private static Stream<Arguments> aValidGetRequests() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH + ALL_CONTEXT_PATH,
                        Method.GET.name(),
                        null,
                        HttpStatus.SC_OK
                )
                ,
                arguments(
                        ROOT_CONTEXT_PATH + FILTER_CONTEXT_PATH + "?name=bit",
                        Method.GET.name(),
                        null,
                        HttpStatus.SC_OK
                )
        );
    }

    @ParameterizedTest
    @MethodSource("aValidGetRequests")
    void shouldRequestGetEndpoints(String contextPath, String method, Recipe requestBody, int statusExpected) {
        ApiTestUtils apiTestUtils = ApiTestUtils.builder().method(method).host(SERVER_HOST).path(contextPath).requestBody(
                Objects.nonNull(requestBody) ? gson.toJson(requestBody) : null
        ).responseType(ApplicationResponse.class).build();
        // when
        ApiTestUtils.TestResponse<ApplicationResponse> res = apiTestUtils.request();
        // then
        assertThat(res.getStatus()).isEqualTo(statusExpected);
        assertThat(res.getBody().getCode()).isEqualTo(ApplicationResponseCodes.SUCCESS);
    }

    private static Stream<Arguments> aValidRequestForNotImplementedYet() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH + ADD_CONTEXT_PATH,
                        Method.POST.name(),
                        Ingredient.builder().amount(4.00).name("Chicken").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + UPDATE_BY_ID_CONTEXT_PATH.replace(":id", "someId"),
                        Method.POST.name(),
                        Ingredient.builder().amount(5.00).name("Chicken fingers").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH + DELETE_BY_ID_CONTEXT_PATH.replace(":id", "anotherId"),
                        Method.DELETE.name(),
                        null
                ),
                arguments(
                        ROOT_CONTEXT_PATH + DELETE_ALL_CONTEXT_PATH,
                        Method.DELETE.name(),
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("aValidRequestForNotImplementedYet")
    void shouldThrowNotImplementedYet(String contextPath, String method, Ingredient requestBody) {
        ApiTestUtils apiTestUtils = ApiTestUtils.builder().method(method).host(SERVER_HOST).path(contextPath).requestBody(
                Objects.nonNull(requestBody) ? gson.toJson(requestBody) : null
        ).responseType(ApplicationResponse.class).build();
        // when
        ApiTestUtils.TestResponse<ApplicationResponse> res = apiTestUtils.request();
        // Then
        assertThat(res.getStatus()).isEqualTo(SC_NOT_IMPLEMENTED);
        assertThat(res.getBody().getCode()).isEqualTo(ApplicationResponseCodes.ERROR);
        assertThat(res.getBody().getException().getErrorCodeType()).isEqualTo(ApplicationErrorType.NOT_IMPLEMENTED);
    }
}
