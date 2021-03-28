package org.subra.aem.rjs.core.restclient.services;

import org.apache.http.Header;
import org.subra.aem.rjs.core.restclient.RestClientResponseDto;
import org.subra.commons.exceptions.RJSApiException;

import java.io.OutputStream;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;


public interface RestClientService {

    <T> T getDataFromResource(final String resourceName, final Class<T> clazz);

    <T> T getData(final String endpointUrl, final String resource, final Map<String, String> requestHeaders, final Map<String, String> queryParams, final Class<T> clazz) throws RJSApiException;

    <T> RestClientResponseDto<T> getDataAndGetResponse(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Class<T> clazz) throws RJSApiException;

    <T> T getData(final String endpointUrl, final String resource, final Map<String, String> requestHeaders, final Map<String, String> queryParams, final Class<T> clazz, int retries) throws RJSApiException;

    <T> RestClientResponseDto<T> getDataAndGetResponse(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Class<T> clazz, int retries) throws RJSApiException;

    void postData(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Object postObject) throws RJSApiException;

    <T> T postData(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Object postObject, Class<T> clazz) throws RJSApiException;

    <T> RestClientResponseDto<T> postDataAndGetResponse(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Object postObject, Class<T> clazz) throws RJSApiException;

    void readBinaryData(String endpointUrl, String resource, Map<String, String> requestHeaders, Map<String, String> queryParams, Consumer<Header> contentTypeConsumer, Supplier<OutputStream> target) throws RJSApiException;

}
