package org.jsanchez.recipe.application.services;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jsanchez.recipe.application.ServerBaseTest;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.jsanchez.recipe.application.test.support.RecipeTestDataFactory.aValidRecipe;

/**
 * Unit tests for {@link RecipeService}
 */
@Slf4j
public class RecipeServiceTest extends ServerBaseTest {

    private RecipeService recipeService;

    @AfterEach
    public void cleanUp() throws ApplicationException {
        recipeService.deleteAll();
    }

    @Test
    public void shouldStoreRecipe() throws ApplicationException {
        // Given
        recipeService = injector.getInstance(RecipeService.class);
        Recipe recipe = aValidRecipe();
        // when no exception
        recipeService.add(recipe);
        // when
        List<Recipe> recipesStored = recipeService.getRecipeByName(recipe.getName(), null);
        //then
        Assertions.assertThat(recipesStored).hasSize(1);
        Assertions.assertThat(recipesStored.get(0).getReference()).isEqualTo(recipe.getReference());
    }

    @Test
    public void shouldGetAll() throws ApplicationException {
        // Given
        recipeService = injector.getInstance(RecipeService.class);
        List<Recipe> recipes = List.of(
                aValidRecipe(),
                aValidRecipe(),
                aValidRecipe()
        );
        recipes.forEach((recipe) -> {
            try {
                recipeService.add(recipe);
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
        });
        // when
        List<Recipe> recipesStored = recipeService.getAll();
        //then
        Assertions.assertThat(recipesStored).hasSize(recipes.size());
    }

    @Test
    public void shouldGetAllRecipesByPartialName() throws ApplicationException {
        // given
        String recipeName = "Some Spanish meal for dinner";
        Recipe recipe = aValidRecipe();
        recipeService = injector.getInstance(RecipeService.class);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName(recipeName);
        recipeService.add(recipe);
        // when
        List<Recipe> recipesStored = recipeService.getRecipeByName("spanish", null);
        // then
        Assertions.assertThat(recipesStored).hasSize(1);
        Assertions.assertThat(recipesStored.get(0).getName()).isEqualTo("Some Spanish meal for dinner");
    }

    @Test
    public void shouldGetAllRecipesByPartialNameOrderByPrepareTimeAsc() throws ApplicationException {
        // given
        Recipe recipe = aValidRecipe();
        recipeService = injector.getInstance(RecipeService.class);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("A meal");
        recipe.setPrepareTime(1.00);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("B meal");
        recipe.setPrepareTime(5.00);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("C meal");
        recipe.setPrepareTime(15.00);
        recipeService.add(recipe);
        // when
        List<Recipe> recipesStored = recipeService.getRecipeByName("meal", null);
        // then
        Assertions.assertThat(recipesStored).hasSize(3);
        log.info("size: {}", recipesStored.size());
        log.info("objects: {}", recipesStored);
        Assertions.assertThat(recipesStored.get(0).getName()).isEqualTo("A meal");

        Assertions.assertThat(recipesStored.get(1).getName()).isEqualTo("B meal");
        Assertions.assertThat(recipesStored.get(2).getName()).isEqualTo("C meal");
    }

    @Test
    public void shouldGetAllRecipesByPartialNameOrderByPrepareTimeDesc() throws ApplicationException {
        // given
        Recipe recipe = aValidRecipe();
        recipeService = injector.getInstance(RecipeService.class);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("A meal");
        recipe.setPrepareTime(20.00);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("B meal");
        recipe.setPrepareTime(35.00);
        recipeService.add(recipe);
        recipe = aValidRecipe();
        recipe.setName("C meal");
        recipe.setPrepareTime(100.00);
        recipeService.add(recipe);
        // when
        List<Recipe> recipesStored = recipeService.getRecipeByName("meal", "desc");
        // then
        Assertions.assertThat(recipesStored).hasSize(3);
        log.info("size: {}", recipesStored.size());
        log.info("objects: {}", recipesStored);
        Assertions.assertThat(recipesStored.get(0).getName()).isEqualTo("C meal");
        Assertions.assertThat(recipesStored.get(1).getName()).isEqualTo("B meal");
        Assertions.assertThat(recipesStored.get(2).getName()).isEqualTo("A meal");
    }
}
