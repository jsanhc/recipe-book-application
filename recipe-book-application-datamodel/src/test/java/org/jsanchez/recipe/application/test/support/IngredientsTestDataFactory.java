package org.jsanchez.recipe.application.test.support;

import org.jsanchez.recipe.application.model.Ingredient;
import org.jsanchez.recipe.application.model.Recipe;

/**
 * Test data factory for {@link Ingredient}
 */
public class IngredientsTestDataFactory {

    public static Ingredient aValidIngredient(Recipe recipe) {
        return Ingredient.builder()
                .amount(2.2)
                .name("chicken")
                .unit("units")
                .recipeToIngredient(recipe)
                .build();
    }

    public static Ingredient aValidIngredient(Recipe recipe, String name) {
        return Ingredient.builder()
                .amount(2.2)
                .name(name)
                .unit("units")
                .recipeToIngredient(recipe)
                .build();
    }

    public static Ingredient aValidIngredient(Recipe recipe, String name, String units) {
        return Ingredient.builder()
                .amount(2.2)
                .name(name)
                .unit(units)
                .recipeToIngredient(recipe)
                .build();
    }
}
