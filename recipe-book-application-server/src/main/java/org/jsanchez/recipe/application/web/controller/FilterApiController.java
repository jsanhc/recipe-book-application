package org.jsanchez.recipe.application.web.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.model.ApplicationErrorResponse;
import org.jsanchez.recipe.application.error.model.ApplicationResponse;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;
import org.jsanchez.recipe.application.web.api.ApplicationApi;

import javax.inject.Inject;
import java.util.Collections;

import static spark.Spark.*;

/**
 * Filters controller:<br>
 * <ul>
 *     <li>Not found filter implementation</li>
 *     <li>{@link ApplicationException} interceptor filter</li>
 *     <li>After after filter implementation (this filter is like as finally filter request flow)</li>
 * </ul>
 */
@Slf4j
public final class FilterApiController implements ApplicationApi {

    @Inject
    private Gson gson;

    @Override
    public void initialize() {
        // The exceptions can't be caught from this filter, the response is build in it.
        // A filter to intercept all request before processed
        before(((request, response) -> log.debug("Request received\nheaders: {}\nbody: {}\nparameters: {}", request.headers(), request.body(), request.queryParams())));

        notFound((request, response) -> {
            log.warn("Endpoint not found");
            response.type(ContentType.APPLICATION_JSON.toString());
            response.status(HttpStatus.SC_NOT_FOUND);
            return gson.toJson(
                    ApplicationResponse.builder()
                            .status(response.status())
                            .code(ApplicationResponseCodes.ERROR)
                            .errors(
                                    Collections.singletonList(
                                            ApplicationErrorResponse.builder()
                                                    .errorCode(ApplicationErrorType.NOT_FOUND)
                                                    .reason("Not found").build()
                                    )
                            )
                            .build()
            );
        });

        internalServerError((request, response) -> {
            log.error("Internal server Error");
            throw new ApplicationException(ApplicationErrorType.INTERNAL_SERVER_ERROR, "Internal server Error");
        });

        // to catch the thrown ApplicationException from controllers
        exception(ApplicationException.class, (exception, request, response) -> {
            log.error("Exception happens:\n{}\n{}", exception.getMessage(), exception.getReason());
            response.type(ContentType.APPLICATION_JSON.toString());
            response.status(exception.getStatus());
            response.body(
                    gson.toJson(
                            ApplicationResponse.builder()
                                    .status(exception.getStatus())
                                    .code(ApplicationResponseCodes.ERROR)
                                    .exception(exception)
                                    .build()
                    )
            );
        });

        // Final filter
        afterAfter((request, response) -> log.debug("Sending response\nstatus: {}\nbody: {}", response.status(), response.body()));
    }
}
