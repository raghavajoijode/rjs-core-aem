package org.subra.aem.rjs.core.commons.exceptions;

/**
 * @author Raghava Joijode
 */
public class RJSCustomException extends Exception {

    private static final long serialVersionUID = 664704840828592278L;

    public RJSCustomException() {
        super();
    }

    public RJSCustomException(final String message) {
        super(message);
    }

    public RJSCustomException(final Throwable cause) {
        super(cause);
    }

    public RJSCustomException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
