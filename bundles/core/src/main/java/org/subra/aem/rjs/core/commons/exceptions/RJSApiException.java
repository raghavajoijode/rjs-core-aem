package org.subra.aem.rjs.core.commons.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 * @author Raghava Joijode
 */
public class RJSApiException extends RuntimeException {

	private static final long serialVersionUID = 664704840828592278L;
	private final String serviceUrl;
	private final String errorResponse;

	private final RJSApiError apiError;

	public RJSApiException(final String message) {
		super(message);
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = new RJSApiError();
	}

	public RJSApiException(final String url, final String message) {
		super(message);
		this.serviceUrl = url;
		this.errorResponse = message;
		this.apiError = new RJSApiError();
	}

	public RJSApiException() {
		super();
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = new RJSApiError();
	}

	public RJSApiException(final String message, final Throwable cause) {
		super(message, cause);
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = new RJSApiError();
	}

	public RJSApiException(final Throwable cause) {
		super(cause);
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = new RJSApiError();
	}

	public RJSApiException(final RJSApiError apiError) {
		super(apiError != null ? apiError.toString() : null);
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = apiError;
	}

	public RJSApiException(final URI uri, final RJSApiError apiError) {
		super(String.format("URL: %s%nError: %s", uri, apiError));
		this.serviceUrl = StringUtils.EMPTY;
		this.errorResponse = StringUtils.EMPTY;
		this.apiError = apiError;
	}

	public RJSApiError getApiError() {
		return apiError;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public String getErrorResponse() {
		return errorResponse;
	}

}
