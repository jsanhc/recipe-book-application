package org.jsanchez.recipe.application.web.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.model.InquiryRequest;
import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.rules.Rule;
import org.jsanchez.recipe.application.rules.factory.FamilyRulesFactory;
import org.jsanchez.recipe.application.services.RecipeService;
import org.jsanchez.recipe.application.types.WorthType;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.api.ApplicationApiController;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.validation.IValidationRequest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static spark.Spark.path;
import static spark.Spark.post;

@Slf4j
public class RulesApiController extends ApplicationApiController {

    private static final String API_NAME = "rulesApi";
    public static final String ROOT_CONTEXT_PATH = "/rules";

    @Inject
    private IValidationRequest validationRequest;

    @Inject
    private RecipeService recipeService;

    @Inject
    private FamilyRulesFactory recipeRulesFactory;

    @Inject
    private Gson gson;
    @Inject
    private RegisterRestApi registerRestApi;

    @Override
    public void initialize() {
        path(ROOT_CONTEXT_PATH, () -> {
            post("", ContentType.APPLICATION_JSON.toString(), (request, response) -> {
                validationRequest.hasValidBodyOtherwiseException(request);
                InquiryRequest inquiryRequest = gson.fromJson(request.body(), InquiryRequest.class);
                Recipe recipe = recipeService.getRecipeByReference(inquiryRequest.getReference());
                response.status(HttpStatus.SC_OK);
                response.type(ContentType.APPLICATION_JSON.toString());
                if (Objects.nonNull(recipe)) {
                    Rule rule = recipeRulesFactory.getRule(inquiryRequest.getWho());
                    return rule.run(inquiryRequest, recipe);
                }
                return InquiryResponse.builder()
                        .reference(inquiryRequest.getReference())
                        .worth(WorthType.INVALID)
                        .build();
            }, gson::toJson);
        });
        // register endpoint operation
        operations.add(
                RestOperation.builder()
                        .operation(Method.POST.name())
                        .path(ROOT_CONTEXT_PATH)
                        .description("It is worth rules (simple family rules)")
                        .build());
        // register api
        registerRestApi.addOperations(API_NAME, operations);
    }
}
