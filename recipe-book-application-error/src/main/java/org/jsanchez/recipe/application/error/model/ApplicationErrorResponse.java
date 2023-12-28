package org.jsanchez.recipe.application.error.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;

@Builder
@Getter
public class ApplicationErrorResponse {
    @Expose
    private ApplicationErrorType errorCode;
    @Expose
    private String reason;
}
