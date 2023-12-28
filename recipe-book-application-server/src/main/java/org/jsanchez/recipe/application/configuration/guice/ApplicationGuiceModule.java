package org.jsanchez.recipe.application.configuration.guice;

import com.google.code.guice.repository.configuration.JpaRepositoryModule;
import com.google.code.guice.repository.configuration.RepositoryBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import lombok.extern.slf4j.Slf4j;
import org.jsanchez.recipe.application.error.handler.ExceptionHandler;
import org.jsanchez.recipe.application.rules.factory.RulesFactory;
import org.jsanchez.recipe.application.types.WorthType;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.types.gson.ApplicationEnumTypeSerializer;
import org.jsanchez.recipe.application.repositories.IngredientRepository;
import org.jsanchez.recipe.application.repositories.RecipeRepository;
import org.jsanchez.recipe.application.rules.DaughterRule;
import org.jsanchez.recipe.application.rules.GrandsonRule;
import org.jsanchez.recipe.application.rules.HusbandRule;
import org.jsanchez.recipe.application.rules.Rule;
import org.jsanchez.recipe.application.rules.factory.FamilyRulesFactory;
import org.jsanchez.recipe.application.services.*;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.types.MealType;
import org.jsanchez.recipe.application.web.api.discover.DiscoveryRestApi;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.controller.*;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.web.discover.DiscoveryRestApiService;
import org.jsanchez.recipe.application.web.discover.RegisterRestApiService;
import org.jsanchez.recipe.application.web.server.BootstrapApplicationServer;
import org.jsanchez.recipe.application.web.validation.IValidationRequest;
import org.jsanchez.recipe.application.web.validation.ValidationRequest;

import static org.jsanchez.recipe.application.configuration.ApplicationConstants.*;
import static org.jsanchez.recipe.application.configuration.ApplicationProperties.PERSISTENCE_UNIT_KEY;

/**
 * Extends {@link AbstractModule} to manage the application component <br>
 * Guice framework provides a DI way o achieve IoC
 */
@Singleton
@Slf4j
public final class ApplicationGuiceModule extends AbstractModule {

    private final ApplicationProperties properties;

    public ApplicationGuiceModule(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        /*
         binding components into guice injection management
         */

        // Application
        bind(ApplicationProperties.class).toInstance(properties);
        bind(BootstrapApplicationServer.class).toInstance(new BootstrapApplicationServer());
        bind(IValidationRequest.class).toInstance(new ValidationRequest());

        // discover services
        bind(RegisterRestApi.class).toInstance(new RegisterRestApiService());
        bind(DiscoveryRestApi.class).toInstance(new DiscoveryRestApiService());

        MapBinder<String, ApplicationApi> mapControllers = MapBinder.newMapBinder(binder(), String.class, ApplicationApi.class);
        mapControllers.addBinding(KEY_GENERIC_CONTROLLER).toInstance(new GenericApiController());
        mapControllers.addBinding(KEY_FILTER_CONTROLLER).toInstance(new FilterApiController());
        mapControllers.addBinding(KEY_DISCOVERY_CONTROLLER).toInstance(new DiscoveryApiController());
        mapControllers.addBinding(KEY_RECIPE_CONTROLLER).toInstance(new RecipeApiController());
        mapControllers.addBinding(KEY_INGREDIENT_CONTROLLER).toInstance(new IngredientApiController());
        mapControllers.addBinding(KEY_RULES_CONTROLLER).toInstance(new RulesApiController());
        // Rules
        bind(RulesFactory.class).toInstance(new FamilyRulesFactory());
        Multibinder<Rule> rulesBinder = Multibinder.newSetBinder(binder(), Rule.class);
        rulesBinder.addBinding().toInstance(new HusbandRule());
        rulesBinder.addBinding().toInstance(new GrandsonRule());
        rulesBinder.addBinding().toInstance(new DaughterRule());
        // model
        bind(IRecipeService.class).toInstance(new RecipeService());
        bind(IIngredientService.class).toInstance(new IngredientService());
        bind(ExceptionHandler.class).toInstance(new ExceptionHandlerService());

        // binding JPA repository module components into guice injection management
        String persistenceUnit = properties.getPropertyAsString(PERSISTENCE_UNIT_KEY);

        // Gson instance configured
        ApplicationEnumTypeSerializer bookEnumTypeSerializer = new ApplicationEnumTypeSerializer();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ApplicationErrorType.class, bookEnumTypeSerializer)
                .registerTypeAdapter(MealType.class, bookEnumTypeSerializer)
                .registerTypeAdapter(FamilyMemberType.class, bookEnumTypeSerializer)
                .registerTypeAdapter(WorthType.class, bookEnumTypeSerializer)
                .setExclusionStrategies().excludeFieldsWithoutExposeAnnotation().create();
        bind(Gson.class).toInstance(gson);

        install(new JpaRepositoryModule(persistenceUnit) {
            @Override
            protected void bindRepositories(RepositoryBinder binder) {
                log.debug("Persistence unit name to bond the repositories: {}", persistenceUnit);
                binder.bind(RecipeRepository.class).to(persistenceUnit);
                binder.bind(IngredientRepository.class).to(persistenceUnit);
            }
        });
    }
}
