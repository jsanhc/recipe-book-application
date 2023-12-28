package org.jsanchez.recipe.application.configuration.guice;

import com.google.gson.Gson;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.jsanchez.recipe.application.ServerBaseTest;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.error.handler.ExceptionHandler;
import org.jsanchez.recipe.application.repositories.IngredientRepository;
import org.jsanchez.recipe.application.repositories.RecipeRepository;
import org.jsanchez.recipe.application.rules.DaughterRule;
import org.jsanchez.recipe.application.rules.GrandsonRule;
import org.jsanchez.recipe.application.rules.HusbandRule;
import org.jsanchez.recipe.application.rules.Rule;
import org.jsanchez.recipe.application.rules.factory.FamilyRulesFactory;
import org.jsanchez.recipe.application.rules.factory.RulesFactory;
import org.jsanchez.recipe.application.services.*;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.api.discover.DiscoveryRestApi;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.controller.*;
import org.jsanchez.recipe.application.web.discover.DiscoveryRestApiService;
import org.jsanchez.recipe.application.web.discover.RegisterRestApiService;
import org.jsanchez.recipe.application.web.server.BootstrapApplicationServer;
import org.jsanchez.recipe.application.web.validation.IValidationRequest;
import org.jsanchez.recipe.application.web.validation.ValidationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * Unit test for {@link ApplicationGuiceModule}
 */
public class ApplicationGuiceModuleTest extends ServerBaseTest {

    @Test
    void shouldHaveBoundControllers() {
        // Given
        Map<String, ApplicationApi> mapControllers = injector.getInstance(
                Key.get(new TypeLiteral<Map<String, ApplicationApi>>() {
                })
        );
        // Then
        assertThat(mapControllers.get(KEY_GENERIC_CONTROLLER)).isInstanceOf(GenericApiController.class);
        assertThat(mapControllers.get(KEY_FILTER_CONTROLLER)).isInstanceOf(FilterApiController.class);
        assertThat(mapControllers.get(KEY_DISCOVERY_CONTROLLER)).isInstanceOf(DiscoveryApiController.class);
        assertThat(mapControllers.get(KEY_INGREDIENT_CONTROLLER)).isInstanceOf(IngredientApiController.class);
        assertThat(mapControllers.get(KEY_RECIPE_CONTROLLER)).isInstanceOf(RecipeApiController.class);
        assertThat(mapControllers.get(KEY_RULES_CONTROLLER)).isInstanceOf(RulesApiController.class);
    }

    @Test
    void shouldHaveBoundRules() {
        // Given
        Set<Rule> rules = injector.getInstance(Key.get(new TypeLiteral<Set<Rule>>() {
        }));
        // then
        assertThat(rules.containsAll(
                List.of(
                        new HusbandRule(),
                        new GrandsonRule(),
                        new DaughterRule()
                )
        ));
        assertThat(injector.getInstance(RulesFactory.class)).isInstanceOf(FamilyRulesFactory.class);
    }

    private static Stream<Arguments> validArguments() {
        // Given
        return Stream.of(
                arguments(
                        BootstrapApplicationServer.class,
                        BootstrapApplicationServer.class
                ), arguments(
                        IValidationRequest.class,
                        ValidationRequest.class
                ),
                arguments(
                        ApplicationProperties.class,
                        ApplicationProperties.class
                ),
                arguments(
                        RegisterRestApi.class,
                        RegisterRestApiService.class
                ),
                arguments(
                        DiscoveryRestApi.class,
                        DiscoveryRestApiService.class
                ),
                arguments(
                        IRecipeService.class,
                        RecipeService.class
                ),
                arguments(
                        IIngredientService.class,
                        IngredientService.class
                ),
                arguments(
                        ExceptionHandler.class,
                        ExceptionHandlerService.class
                ),
                arguments(
                        Gson.class,
                        Gson.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void shouldHaveInstanceOf(Class type, Class expectedInstance) {
        assertThat(injector.getInstance(type)).isInstanceOf(expectedInstance);
    }

    @Test
    void shouldHaveJpaRepositories() {
        assertThat(injector.getBinding(Key.get(RecipeRepository.class))).isNotNull();
        assertThat(injector.getBinding(Key.get(RecipeRepository.class)).getProvider().get()).isInstanceOf(RecipeRepository.class);
        assertThat(injector.getBinding(Key.get(IngredientRepository.class))).isNotNull();
        assertThat(injector.getBinding(Key.get(IngredientRepository.class)).getProvider().get()).isInstanceOf(IngredientRepository.class);
    }
}
