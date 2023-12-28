package org.jsanchez.recipe.application.test.support;

import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.types.MealType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.jsanchez.recipe.application.test.support.IngredientsTestDataFactory.aValidIngredient;

public class RecipeTestDataFactory {

    public static final Recipe aValidRecipe() {
        String reference = UUID.randomUUID().toString();
        Recipe recipe = Recipe.builder()
                .reference(reference)
                .name("Name-" + reference)
                .meal(MealType.DINNER)
                .prepareTime(2.5)
                .build();

        recipe.setIngredients(
                new ArrayList<>(
                        Arrays.asList(
                                aValidIngredient(recipe),
                                aValidIngredient(recipe),
                                aValidIngredient(recipe)
                        )
                )
        );
        return recipe;
    }

    public static final Recipe aValidRecipe(String reference) {
        Recipe recipe = Recipe.builder()
                .reference(reference)
                .name("Name-" + reference)
                .meal(MealType.DINNER)
                .prepareTime(2.5)
                .build();

        recipe.setIngredients(
                new ArrayList<>(
                        Arrays.asList(
                                aValidIngredient(recipe),
                                aValidIngredient(recipe),
                                aValidIngredient(recipe)
                        )
                )
        );
        return recipe;
    }
}
