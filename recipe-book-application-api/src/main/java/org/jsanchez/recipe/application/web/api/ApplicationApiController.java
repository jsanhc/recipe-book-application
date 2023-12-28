package org.jsanchez.recipe.application.web.api;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Application abstract class
 */
public abstract class ApplicationApiController implements ApplicationApi {

    protected List<RestOperation> operations = new ArrayList<>();

    public List<RestOperation> getOperations(){ return operations; }
}
