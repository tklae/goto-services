package com.gotocon.cdworkshop.http;

import com.gotocon.cdworkshop.exception.ExceptionMapper;
import com.gotocon.cdworkshop.logging.ServiceMDC;
import com.yammer.dropwizard.client.HttpClientBuilder;
import com.yammer.dropwizard.client.HttpClientConfiguration;
import com.yammer.dropwizard.util.Duration;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class ApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    public static final String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    public static final String MEDIA_TYPE_XML = MediaType.APPLICATION_XML + ";charset=UTF-8";

    private final HttpClient httpClient;
    private final String contentType;
    private final ExceptionMapper exceptionMapper;

    public ApiClient(String contentType, ExceptionMapper exceptionMapper) {
        this.contentType = contentType;
        this.exceptionMapper = exceptionMapper;
        HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();
        httpClientConfiguration.setTimeout(Duration.seconds(30));
        httpClientConfiguration.setConnectionTimeout(Duration.seconds(30));
        httpClientConfiguration.setTimeToLive(Duration.seconds(10));
        httpClient = new HttpClientBuilder().using(httpClientConfiguration).build();
    }

    public ApiClient(HttpClient httpClient, String contentType, ExceptionMapper exceptionMapper) {
        this.contentType = contentType;
        this.exceptionMapper = exceptionMapper;
        this.httpClient = httpClient;
    }

    public ApiHttpResponse get(String resourceUri) throws IOException {
        HttpGet request = new HttpGet(resourceUri);
        addGenericHeaders(request);
        try {
            HttpResponse response = httpClient.execute(request);
            ApiHttpResponse apiHttpResponse = new ApiHttpResponse(response.getEntity(), response.getStatusLine().getStatusCode());
            checkResponse(resourceUri, apiHttpResponse);
            return apiHttpResponse;
        } finally {
            request.releaseConnection();
            LOGGER.debug("GET Request Served "+resourceUri);
        }
    }

    public ApiHttpResponse post(String resourceUri, String requestBody) throws IOException {
        HttpPost request = new HttpPost(resourceUri);
        addGenericHeaders(request);
        request.setHeader("Content-Type", contentType);
        request.setEntity(new StringEntity(requestBody));
        try {
            HttpResponse response = httpClient.execute(request);
            ApiHttpResponse apiHttpResponse = new ApiHttpResponse(response.getEntity(), response.getStatusLine().getStatusCode());
            checkResponse(resourceUri, apiHttpResponse);
            return apiHttpResponse;
        } finally {
            request.releaseConnection();
            LOGGER.debug("POST Request Served "+resourceUri);
        }
    }

    private void checkResponse(String resourceUri, ApiHttpResponse response) {
        if (!response.isOk()) {
            LOGGER.error("Error while fetching " + resourceUri + " got code " + response.getStatusCode());
            throw exceptionMapper.mapErrorResponse(response);
        }
    }

    private void addGenericHeaders(HttpRequestBase request) {
        request.addHeader(HttpHeaders.SESSION_ID_KEY, ServiceMDC.getSessionId());
        request.addHeader(HttpHeaders.REQUEST_ID_KEY, ServiceMDC.getRequestId());
    }

}
