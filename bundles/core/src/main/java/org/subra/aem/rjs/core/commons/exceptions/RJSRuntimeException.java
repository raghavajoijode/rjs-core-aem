package org.subra.aem.rjs.core.commons.exceptions;

/**
 * @author Raghava Joijode
 */
public class RJSRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 664704840828592278L;

    public RJSRuntimeException() {
        super();
    }

    public RJSRuntimeException(final String message) {
        super(message);
    }

    public RJSRuntimeException(final Throwable cause) {
        super(cause);
    }

    public RJSRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
