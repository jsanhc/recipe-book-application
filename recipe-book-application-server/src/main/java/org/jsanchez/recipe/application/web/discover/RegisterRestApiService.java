package org.jsanchez.recipe.application.web.discover;

import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class RegisterRestApiService implements RegisterRestApi<RestOperation, RegisterRestApiService> {

    private final Map<String, List<RestOperation>> operations = new HashMap<>();
    private final Map<String, Map<String, List<RestOperation>>> apis = new HashMap<>();

    @Override
    public RegisterRestApiService addOperations(String name, List<RestOperation> operationList) {
        operations.put(name, operationList);
        return this;
    }

    @Override
    public Map<String, Map<String, List<RestOperation>>> getApis() {
        apis.put("applicationApi", operations);
        return this.apis;
    }
}
