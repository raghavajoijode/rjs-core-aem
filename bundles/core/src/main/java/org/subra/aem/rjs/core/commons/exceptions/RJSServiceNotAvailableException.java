package org.subra.aem.rjs.core.commons.exceptions;

/**
 * @author Raghava Joijode
 */
public class RJSServiceNotAvailableException extends RJSApiException {

    private static final long serialVersionUID = -7005308022960499722L;

    public RJSServiceNotAvailableException() {
        super();
    }

    public RJSServiceNotAvailableException(RJSApiError apiError) {
        super(apiError);
    }

    public RJSServiceNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RJSServiceNotAvailableException(String message) {
        super(message);
    }

    public RJSServiceNotAvailableException(Throwable cause) {
        super(cause);
    }

}
