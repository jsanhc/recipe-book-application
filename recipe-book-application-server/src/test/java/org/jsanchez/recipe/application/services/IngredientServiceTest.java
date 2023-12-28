package org.jsanchez.recipe.application.services;

import org.assertj.core.api.Assertions;
import org.jsanchez.recipe.application.ServerBaseTest;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.model.Ingredient;
import org.jsanchez.recipe.application.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.jsanchez.recipe.application.test.support.IngredientsTestDataFactory.aValidIngredient;
import static org.jsanchez.recipe.application.test.support.RecipeTestDataFactory.aValidRecipe;

/**
 * Unit test for {@link IngredientService}
 */
public class IngredientServiceTest extends ServerBaseTest {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    @AfterEach
    public void cleanUp() throws ApplicationException {
        recipeService.deleteAll();
    }

    @Test
    public void shouldGetAll() throws ApplicationException {
        /// Given
        ingredientService = injector.getInstance(IngredientService.class);
        recipeService = injector.getInstance(RecipeService.class);
        Recipe recipe = aValidRecipe();
        recipeService.add(recipe);
        // when
        List<Ingredient> ingredients = ingredientService.getAll();
        //then
        Assertions.assertThat(ingredients).hasSize(recipe.getIngredients().size());
    }

    @Test
    public void shouldGetIngredientByPartialName() throws ApplicationException {
        /// Given
        String ingredientContentName = "1F4G2B";
        String recipeName = "Some Spanish meal for dinner";
        ingredientService = injector.getInstance(IngredientService.class);
        recipeService = injector.getInstance(RecipeService.class);
        Recipe recipe = aValidRecipe();
        recipe.addIngredient(aValidIngredient(recipe, "Special " + ingredientContentName + " ingredient"));
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName(recipeName);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName(recipeName);
        recipe.addIngredient(aValidIngredient(recipe, "Special " + ingredientContentName + " ingredient"));
        recipeService.add(recipe);
        // when
        List<Ingredient> ingredients = ingredientService.getIngredientByName(ingredientContentName, null);
        //then
        Assertions.assertThat(ingredients).hasSize(2);
    }
}
