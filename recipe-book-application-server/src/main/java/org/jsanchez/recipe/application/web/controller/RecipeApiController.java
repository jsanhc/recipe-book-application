package org.jsanchez.recipe.application.web.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.services.IRecipeService;
import org.jsanchez.recipe.application.web.api.ApplicationApiController;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.discover.RegisterRestApiService;
import org.jsanchez.recipe.application.web.validation.IValidationRequest;

import javax.inject.Inject;
import java.util.List;

import static spark.Spark.*;

/**
 * Recipe controller:<br>
 * <ul>
 *     <li>Recipe endpoint implementation</li>
 *     <li>Register recipe api</li>
 * </ul>
 * @see RegisterRestApiService
 */
@Slf4j
public class RecipeApiController extends ApplicationApiController {
    private static final String API_NAME = "recipeApi";
    public static final String ROOT_CONTEXT_PATH = "/recipes";
    public static final String ALL_CONTEXT_PATH = "/all";
    public static final String FILTER_CONTEXT_PATH = "/filter";
    public static final String ADD_CONTEXT_PATH = "/add";
    public static final String UPDATE_BY_ID_CONTEXT_PATH = "/update/:id";
    public static final String DELETE_BY_ID_CONTEXT_PATH = "/delete/:id";
    public static final String DELETE_ALL_CONTEXT_PATH = "/delete/all";

    private static final String NAME_QUERY_PARAM = "name";
    private static final String SORT_QUERY_PARAM = "sort";
    @Inject
    private IRecipeService recipeService;

    @Inject
    private IValidationRequest validationApi;

    @Inject
    private Gson gson;

    @Inject
    private RegisterRestApi registerRestApi;

    @Override
    public void initialize() {
        path(ROOT_CONTEXT_PATH, () -> {
            get(ALL_CONTEXT_PATH, (request, response) -> {
                response.status(HttpStatus.SC_OK);
                response.type(ContentType.APPLICATION_JSON.toString());

                return ApplicationResponse.builder()
                        .status(response.status())
                        .code(ApplicationResponseCodes.SUCCESS)
                        .data(
                                gson.toJsonTree(recipeService.getAll())
                        )
                        .build();
            }, gson::toJson);

            get(FILTER_CONTEXT_PATH, (request, response) -> {
                validationApi.hasExpectedQueryParamsOtherwiseException(request, NAME_QUERY_PARAM);

                String name = request.queryParams(NAME_QUERY_PARAM).trim().toLowerCase();
                String sort = validationApi.hasQueryParams(request, SORT_QUERY_PARAM) ?
                        request.queryParams(SORT_QUERY_PARAM).trim().toLowerCase() :
                        null;

                log.debug("Value '{}' to search recipe by partial name", name);
                response.type(ContentType.APPLICATION_JSON.toString());
                response.status(HttpStatus.SC_OK);
                return ApplicationResponse.builder()
                        .status(response.status())
                        .code(ApplicationResponseCodes.SUCCESS)
                        .data(
                                gson.toJsonTree(recipeService.getRecipeByName(name, sort))
                        )
                        .build();

            }, gson::toJson);

            post(ADD_CONTEXT_PATH, ContentType.APPLICATION_JSON.toString(), (request, response) -> {
                validationApi.hasValidBodyOtherwiseException(request);
                log.debug("body:{}", request.body());
                Recipe recipe = gson.fromJson(request.body(), Recipe.class);
                Recipe recipeAdded = recipeService.add(recipe);
                response.status(HttpStatus.SC_CREATED);
                response.type(ContentType.APPLICATION_JSON.toString());
                return ApplicationResponse.builder()
                        .status(response.status())
                        .code(ApplicationResponseCodes.SUCCESS)
                        .data(gson.toJsonTree(recipeAdded))
                        .build();
            }, gson::toJson);

            post(UPDATE_BY_ID_CONTEXT_PATH, ContentType.APPLICATION_JSON.toString(), (request, response) -> {
                throw new ApplicationException(
                        HttpStatus.SC_NOT_IMPLEMENTED,
                        ApplicationErrorType.NOT_IMPLEMENTED,
                        "Operation update not implemented yet"
                );
            }, gson::toJson);

            delete(DELETE_BY_ID_CONTEXT_PATH, (request, response) -> {
                throw new ApplicationException(
                        HttpStatus.SC_NOT_IMPLEMENTED,
                        ApplicationErrorType.NOT_IMPLEMENTED,
                        "Operation update not implemented yet"
                );
            }, gson::toJson);

            delete(DELETE_ALL_CONTEXT_PATH, (request, response) -> {
                throw new ApplicationException(
                        HttpStatus.SC_NOT_IMPLEMENTED,
                        ApplicationErrorType.NOT_IMPLEMENTED,
                        "Operation update not implemented yet"
                );
            }, gson::toJson);
        });

        // register api operations
        operations.addAll(List.of(
                        RestOperation.builder()
                                .operation(Method.GET.name())
                                .path(ROOT_CONTEXT_PATH + ALL_CONTEXT_PATH)
                                .description("Get all recipes")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.GET.name())
                                .path(ROOT_CONTEXT_PATH + FILTER_CONTEXT_PATH)
                                .description("Get all recipes filter by partial name order by 'prepare time' ascendant as default, optional parameter to sort them 'sort=desc'")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.POST.name())
                                .path(ROOT_CONTEXT_PATH + ADD_CONTEXT_PATH)
                                .description("Add an recipe")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.POST.name())
                                .path(ROOT_CONTEXT_PATH + UPDATE_BY_ID_CONTEXT_PATH)
                                .description("Update an recipe by id")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.DELETE.name())
                                .path(ROOT_CONTEXT_PATH + DELETE_BY_ID_CONTEXT_PATH)
                                .description("Delete an recipe by id")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.DELETE.name())
                                .path(ROOT_CONTEXT_PATH + DELETE_ALL_CONTEXT_PATH)
                                .description("Delete all recipes")
                                .build()
                )
        );

        // register api
        registerRestApi.addOperations(API_NAME, operations);
    }
}
