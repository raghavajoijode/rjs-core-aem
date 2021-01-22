package org.subra.aem.rjs.core.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * @author Raghava Joijode
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class RJSApiError implements Serializable {

    private static final long serialVersionUID = -9145395656420154303L;

    private int httpStatusCode;
    private String httpReason;

    @JsonProperty("code")
    private String errorCode;

    @JsonProperty("message")
    private String errorMessage;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(final int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpReason() {
        return httpReason;
    }

    public void setHttpReason(final String httpReason) {
        this.httpReason = httpReason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "HttpStatus: [" + httpStatusCode + "] :: httpReason [" + httpReason + "] :: Api error code: [" + errorCode + "] :: Api error message: [" + errorMessage + "]";
    }
}
