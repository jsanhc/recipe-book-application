package org.jsanchez.recipe.application.web.discover;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jsanchez.recipe.application.web.api.discover.DiscoveryRestApi;
import org.jsanchez.recipe.application.web.api.discover.RegisterRestApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;

/**
 * Service to populate the application apis registered in {@link RegisterRestApiService}
 */
@Singleton
public final class DiscoveryRestApiService implements DiscoveryRestApi {

    @Inject
    private RegisterRestApi registerRestApiService;

    @Override
    public Map<String, Map<String, List<RestOperation>>> getApis() {
        return registerRestApiService.getApis();
    }
}
