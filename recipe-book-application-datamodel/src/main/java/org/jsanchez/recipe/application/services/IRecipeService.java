package org.jsanchez.recipe.application.services;

import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.error.ApplicationException;

import java.util.List;

/**
 * Definition of data access operations for the entity {@link Recipe}
 */
public interface IRecipeService {

    List<Recipe> getAll() throws ApplicationException;

    List<Recipe> getRecipeByName(String partialName, String sorting) throws ApplicationException;

    Recipe getRecipeByReference(String reference) throws ApplicationException;

    Recipe add(Recipe recipeDetails) throws ApplicationException;

    void deleteAll() throws ApplicationException;
}
