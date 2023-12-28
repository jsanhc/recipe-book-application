package org.jsanchez.recipe.application.repositories;

import org.jsanchez.recipe.application.model.Member;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  Extends {@link JpaRepository} to reducing the persistence layer boilerplate code to access the data
 */
public interface RecipeRepository extends JpaRepository<Recipe, String> {

    List<Recipe> findByNameContainingIgnoreCase(String partialName);

    List<Recipe> findByNameContainingIgnoreCaseOrderByPrepareTimeDesc(String partialName);

    List<Recipe> findByNameContainingIgnoreCaseOrderByPrepareTimeAsc(String partialName);

    Recipe findByReference(String reference);

}
