package org.jsanchez.recipe.application.web.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.web.api.ApplicationApiController;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.discover.RegisterRestApiService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

/**
 * Generic controller subclass of {@link ApplicationApiController}:<br>
 * <ul>
 *     <li>Application generic endpoints implementation</li>
 *     <li>Application filters implementation</li>
 * </ul>
 * @see RegisterRestApiService
 */
@Slf4j
public class GenericApiController extends ApplicationApiController {
    private static final String API_NAME = "genericApi";
    public static final String ROOT_CONTEXT_PATH = "/";

    public static final String HEALTH_CONTEXT_PATH = ROOT_CONTEXT_PATH + "health";

    @Inject
    private Gson gson;
    @Inject
    private RegisterRestApi registerRestApi;

    @Override
    public void initialize() {
        // Welcome endpoint
        get(ROOT_CONTEXT_PATH, (request, response) -> {
            response.status(HttpStatus.SC_OK);
            response.type(ContentType.APPLICATION_JSON.toString());
            Map<String, String> data = new HashMap<>();
            data.put("welcome", "Welcome grandma! to your electronic recipe application.");
            data.put("phrase-of-the-week", "The excellence is not an act, but a habit");
            data.put("version", "1.0.0");
            data.put("author", "Jorge Sanchez");
            return ApplicationResponse.builder()
                    .status(response.status())
                    .code(ApplicationResponseCodes.SUCCESS)
                    .data(gson.toJsonTree(data))
                    .build();
        }, gson::toJson);
        // health endpoint
        get(HEALTH_CONTEXT_PATH, (request, response) -> {
            response.status(HttpStatus.SC_OK);
            response.type(ContentType.APPLICATION_JSON.toString());
            return ApplicationResponse.builder()
                    .status(response.status())
                    .code(ApplicationResponseCodes.SUCCESS)
                    .build();
        }, gson::toJson);
        // register endpoint operations
        operations.addAll(
                List.of(
                        RestOperation.builder()
                                .operation(Method.GET.name())
                                .path(ROOT_CONTEXT_PATH)
                                .description("Welcome")
                                .build(),
                        RestOperation.builder()
                                .operation(Method.GET.name())
                                .path(HEALTH_CONTEXT_PATH)
                                .description("Health")
                                .build()
                )
        );
        // register api
        registerRestApi.addOperations(API_NAME, operations);
    }

}
