package org.jsanchez.recipe.application.error;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import org.apache.hc.core5.http.HttpStatus;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;

/**
 * Application exception event object
 */
@Getter
public class ApplicationException extends Exception {
    private final int status;
    @Expose
    private final ApplicationErrorType errorCodeType;
    @Expose
    private final String reason;

    public ApplicationException(Exception exception, ApplicationErrorType errorCodeType, String reason) {
        super(exception);
        this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.errorCodeType = errorCodeType;
        this.reason = reason;
    }

    public ApplicationException(int status, ApplicationErrorType errorCodeType, String reason) {
        super(reason);
        this.status = status;
        this.errorCodeType = errorCodeType;
        this.reason = reason;
    }

    public ApplicationException(ApplicationErrorType errorCodeType, String reason) {
        super(reason);
        this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.errorCodeType = errorCodeType;
        this.reason = reason;
    }
}
