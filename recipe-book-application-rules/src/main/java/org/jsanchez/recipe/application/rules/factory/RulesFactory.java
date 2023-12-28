package org.jsanchez.recipe.application.rules.factory;

import org.jsanchez.recipe.application.rules.Rule;
import org.jsanchez.recipe.application.error.ApplicationException;

/**
 * Generic Rules factory interface
 * @param <T> parametrized type
 */
public interface RulesFactory<T> {
    /**
     * Returns an instance of {@link Rule}
     * @param type parametrized type
     * @return instance of {@link Rule}
     * @throws ApplicationException
     */
    Rule getRule(T type) throws ApplicationException;
}
