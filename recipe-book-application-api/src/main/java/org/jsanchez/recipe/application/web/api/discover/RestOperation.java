package org.jsanchez.recipe.application.web.api.discover;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class RestOperation {
    @Expose
    private String operation;
    @Expose
    private String path;
    @Expose
    private String description;
}
