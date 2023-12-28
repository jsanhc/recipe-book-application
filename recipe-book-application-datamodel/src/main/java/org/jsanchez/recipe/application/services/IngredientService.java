package org.jsanchez.recipe.application.services;

import com.google.common.base.Strings;
import org.jsanchez.recipe.application.error.handler.ExceptionHandler;
import org.jsanchez.recipe.application.model.Ingredient;
import org.jsanchez.recipe.application.repositories.IngredientRepository;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Ingredient service implementation {@link IIngredientService}
 */
public class IngredientService implements IIngredientService {

    @Inject
    private IngredientRepository ingredientRepository;

    @Inject
    private ExceptionHandler exceptionHandlerService;

    @Override
    public List<Ingredient> getAll() throws ApplicationException {
        try {
            return ingredientRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
    }

    @Override
    public List<Ingredient> getIngredientByName(String partialName, String sorting) throws ApplicationException {
        List<Ingredient> ingredients;
        try {
            if (!Strings.isNullOrEmpty(sorting) && "desc".equals(sorting)) {
                ingredients = ingredientRepository.findByNameContainingIgnoreCaseOrderByNameDesc(partialName);
            } else {
                ingredients = ingredientRepository.findByNameContainingIgnoreCaseOrderByNameAsc(partialName);
            }
        } catch (Exception exception) {
            throw exceptionHandlerService.exceptionHandler(exception);
        }
        return ingredients;
    }

    @Transactional
    @Override
    public Ingredient update(Ingredient ingredientDetails) throws ApplicationException {
        // TODO: not implemented yet
        throw exceptionHandlerService.exceptionHandler(
                new ApplicationException(ApplicationErrorType.NOT_IMPLEMENTED, "Not implemented yet")
        );
    }

    @Transactional
    @Override
    public void deleteIngredientById(Long id) throws ApplicationException {
        // TODO: not implemented yet
        throw exceptionHandlerService.exceptionHandler(
                new ApplicationException(ApplicationErrorType.NOT_IMPLEMENTED, "Not implemented yet")
        );
    }

    @Transactional
    @Override
    public Ingredient add(Ingredient recipeDetails) throws ApplicationException {
        // TODO: not implemented yet
        throw exceptionHandlerService.exceptionHandler(
                new ApplicationException(ApplicationErrorType.NOT_IMPLEMENTED, "Not implemented yet")
        );
    }

    @Transactional
    @Override
    public void deleteAll() throws ApplicationException {
        // TODO: not implemented yet
        throw exceptionHandlerService.exceptionHandler(
                new ApplicationException(ApplicationErrorType.NOT_IMPLEMENTED, "Not implemented yet")
        );
    }
}
