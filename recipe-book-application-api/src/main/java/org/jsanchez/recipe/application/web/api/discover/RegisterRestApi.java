package org.jsanchez.recipe.application.web.api.discover;

import java.util.List;
import java.util.Map;

/**
 * Interface to implement the logic to register the rest operations
 */
public interface RegisterRestApi<T, R> {
    R addOperations(String name, List<T> operations);

    Map<String, Map<String, List<RestOperation>>> getApis();
}
