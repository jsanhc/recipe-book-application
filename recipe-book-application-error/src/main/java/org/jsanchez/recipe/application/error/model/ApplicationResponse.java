package org.jsanchez.recipe.application.error.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationResponseCodes;

import java.util.List;


/**
 * Response class
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApplicationResponse {

    @Expose
    private int status;
    @Expose
    private ApplicationResponseCodes code;
    @Expose
    private JsonElement data;
    @Expose
    private List<ApplicationErrorResponse> errors;

    @Expose
    private ApplicationException exception;

}
