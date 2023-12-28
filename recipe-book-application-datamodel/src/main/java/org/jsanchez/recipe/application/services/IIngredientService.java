package org.jsanchez.recipe.application.services;


import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.model.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Definition of data access operations for the entity {@link Ingredient}
 */
public interface IIngredientService {
    List<Ingredient> getAll() throws ApplicationException;

    List<Ingredient> getIngredientByName(String partialName, String sorting) throws ApplicationException;

    Ingredient update(Ingredient ingredientDetails) throws ApplicationException;

    void deleteIngredientById(Long id) throws ApplicationException;

    Ingredient add(Ingredient recipeDetails) throws ApplicationException;

    @Transactional
    void deleteAll() throws ApplicationException;
}
