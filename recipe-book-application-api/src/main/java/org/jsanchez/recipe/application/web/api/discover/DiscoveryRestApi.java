package org.jsanchez.recipe.application.web.api.discover;

import java.util.List;
import java.util.Map;

/**
 * Interface to populate the rest endpoint from the discovery endpoint
 */
public interface DiscoveryRestApi {
    Map<String, Map<String, List<RestOperation>>> getApis();
}
