package org.jsanchez.recipe.application.web.controller;

import com.google.gson.Gson;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.web.api.ApplicationApiController;
import org.jsanchez.recipe.application.web.api.discover.DiscoveryRestApi;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.discover.RegisterRestApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

import static spark.Spark.get;

/**
 * Discovery controller:<br>
 * <ul>
 *     <li>Application discovery endpoint implementation</li>
 *     <li>Register discovery api</li>
 * </ul>
 * @see RegisterRestApiService
 */
@Singleton
public final class DiscoveryApiController extends ApplicationApiController {

    private static final String API_NAME = "discoveryApi";
    public static final String ROOT_CONTEXT_PATH = "/discovery";
    @Inject
    private Gson gson;
    @Inject
    private RegisterRestApi registerRestApi;
    @Inject
    private DiscoveryRestApi discoveryRestApiService;

    @Override
    public void initialize() {
        get(ROOT_CONTEXT_PATH, (request, response) -> {
            response.status(HttpStatus.SC_OK);
            response.type(ContentType.APPLICATION_JSON.toString());

            return ApplicationResponse.builder()
                    .status(response.status())
                    .code(ApplicationResponseCodes.SUCCESS)
                    .data(gson.toJsonTree(discoveryRestApiService.getApis()))
                    .build();
        }, gson::toJson);
        // register endpoint operation
        operations.add(
                RestOperation.builder()
                        .path(ROOT_CONTEXT_PATH)
                        .description("Discovery")
                        .operation(Method.GET.name())
                        .build()
        );
        // register api
        registerRestApi.addOperations(API_NAME, operations);
    }
}
