package org.subra.aem.rjs.core.commons.exceptions;

/**
 * @author Raghava Joijode
 */
public class RJSConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -6398099054005140577L;

    public RJSConfigurationException() {
        super();
    }

    public RJSConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RJSConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RJSConfigurationException(String message) {
        super(message);
    }

    public RJSConfigurationException(Throwable cause) {
        super(cause);
    }

}
