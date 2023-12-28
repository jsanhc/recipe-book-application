package org.jsanchez.recipe.application.repositories;

import org.jsanchez.recipe.application.model.Ingredient;
import org.jsanchez.recipe.application.model.Recipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  Extends {@link JpaRepository} to reducing the persistence layer boilerplate code to access the data
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByRecipeToIngredient(Recipe recipe);

    List<Ingredient> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Ingredient> findByNameContainingIgnoreCaseOrderByNameAsc(String partialName);

    List<Ingredient> findByNameContainingIgnoreCaseOrderByNameDesc(String partialName);
}
