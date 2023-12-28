package org.jsanchez.recipe.application.services;

import com.google.common.base.Strings;
import org.jsanchez.recipe.application.error.handler.ExceptionHandler;
import org.jsanchez.recipe.application.model.Member;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.repositories.RecipeRepository;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IRecipeService}
 */
public class RecipeService implements IRecipeService {

    @Inject
    private RecipeRepository recipeRepository;

    @Inject
    IIngredientService ingredientService;

    @Inject
    private ExceptionHandler exceptionHandlerService;

    @Override
    public List<Recipe> getAll() throws ApplicationException {
        try {
            return recipeRepository.findAll();
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
    }

    @Transactional
    @Override
    public Recipe add(Recipe recipeDetails) throws ApplicationException {
        Recipe recipe = recipeRepository.findByReference(recipeDetails.getReference());
        if (Objects.nonNull(recipe)) {
            throw new ApplicationException(
                    ApplicationErrorType.DATABASE_DUPLICATE_ENTRY_ERROR,
                    "Constraint violation, a recipe with the same reference [" + recipeDetails.getReference() + "] exist.");
        }
        try {
            if (Objects.isNull(recipeDetails.getReference())) {
                recipeDetails.generateReference();
            }
            return recipeRepository.save(recipeDetails);
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
    }

    @Transactional
    @Override
    public void deleteAll() throws ApplicationException {
        try {
            recipeRepository.deleteAll();
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
    }

    @Override
    public List<Recipe> getRecipeByName(String partialName, String sorting) throws ApplicationException {
        List<Recipe> recipes;
        try {
            if (!Strings.isNullOrEmpty(sorting) && "desc".equals(sorting)) {
                recipes = recipeRepository.findByNameContainingIgnoreCaseOrderByPrepareTimeDesc(partialName);
            } else {
                recipes = recipeRepository.findByNameContainingIgnoreCaseOrderByPrepareTimeAsc(partialName);
            }
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
        return recipes;
    }

    @Override
    public Recipe getRecipeByReference(String reference) throws ApplicationException {
        return recipeRepository.findByReference(reference);
    }
}
