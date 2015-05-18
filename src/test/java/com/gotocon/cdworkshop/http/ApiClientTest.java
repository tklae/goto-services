package com.gotocon.cdworkshop.http;

import com.gotocon.cdworkshop.exception.ExceptionMapper;
import com.gotocon.cdworkshop.exception.ServiceException;
import com.gotocon.cdworkshop.logging.ServiceMDC;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ApiClientTest {

    private HttpClient httpClient;
    private String uri;
    private HttpResponse response;
    private StatusLine statusLine;
    private HttpParams params;
    private String defaultContent;
    private ApiClient apiClient;
    private ExceptionMapper exceptionMapper;

    @Before
    public void setup() throws Exception {
        httpClient = mock(HttpClient.class);
        uri = "http://localhost:8100/someendpoint";
        response = mock(HttpResponse.class);
        statusLine = mock(StatusLine.class);
        params = mock(HttpParams.class);
        exceptionMapper = mock(ExceptionMapper.class);
        defaultContent = "Some content";

        given(statusLine.getStatusCode()).willReturn(200);
        given(response.getStatusLine()).willReturn(statusLine);
        given(response.getEntity()).willReturn(new StringEntity(defaultContent));

        given(httpClient.execute(any(HttpGet.class))).willReturn(response);
        given(params.getIntParameter(any(String.class), any(Integer.class))).willReturn(1);
        given(httpClient.getParams()).willReturn(params);

        this.apiClient = new ApiClient(httpClient, ApiClient.MEDIA_TYPE_JSON, exceptionMapper);
    }

    @Test
    public void shouldSubmitGetRequest() throws Exception {
        // when
        apiClient.get(uri);

        // then
        ArgumentCaptor<HttpGet> argument = ArgumentCaptor.forClass(HttpGet.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getMethod(), is("GET"));
        assertThat(argument.getValue().getURI().toString(), is(uri));
    }

    @Test
    public void shouldSubmitPostRequest() throws Exception {
        // when
        String body = "Hello";
        apiClient.post(uri, body);

        // then
        ArgumentCaptor<HttpPost> argument = ArgumentCaptor.forClass(HttpPost.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getMethod(), is("POST"));
        assertThat(argument.getValue().getURI().toString(), is(uri));
        assertThat(EntityUtils.toString(argument.getValue().getEntity()), is(body));
    }

    @Test
    public void shouldUseCorrectContentType() throws Exception {
        // when
        String contentType = "SomeFunnyContentType";
        this.apiClient = new ApiClient(httpClient, contentType, exceptionMapper);
        apiClient.post(uri, "Body");

        // then
        ArgumentCaptor<HttpPost> argument = ArgumentCaptor.forClass(HttpPost.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getHeaders("Content-Type")[0].getValue(), is(contentType));
    }

    @Test
    public void shouldCreateGetResponse() throws Exception {
        // when
        ApiHttpResponse response = apiClient.get(uri);

        // then
        assertThat(response.isOk(), is(true));
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getPayload(), is(defaultContent));
    }

    @Test
    public void shouldCreatePostResponse() throws Exception {
        // when
        ApiHttpResponse response = apiClient.post(uri, "Hello");

        // then
        assertThat(response.isOk(), is(true));
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getPayload(), is(defaultContent));
    }


    @Test
    public void shouldHandleErrorResponseForGet() throws Exception {
        // Given
        given(statusLine.getStatusCode()).willReturn(500);
        given(exceptionMapper.mapErrorResponse(any(ApiHttpResponse.class))).willReturn(mock(ServiceException.class));

        // when
        try {
            apiClient.get(uri);
        } catch (ServiceException se) {
            verify(exceptionMapper).mapErrorResponse(any(ApiHttpResponse.class));
        }
    }

    @Test
    public void shouldHandleErrorResponseForPost() throws Exception {
        // Given
        given(statusLine.getStatusCode()).willReturn(410);
        given(exceptionMapper.mapErrorResponse(any(ApiHttpResponse.class))).willReturn(mock(ServiceException.class));

        // when
        try {
            apiClient.post(uri, defaultContent);
        } catch (ServiceException se) {
            verify(exceptionMapper).mapErrorResponse(any(ApiHttpResponse.class));
        }
    }

    @Test
    public void shouldAddSessionIdAndRequestIdHeaderForGet() throws Exception {
        // when
        String sessionId = "someSessionId";
        String requestId = "someRequestId";
        ServiceMDC.setSessionId(sessionId);
        ServiceMDC.setRequestId(requestId);
        apiClient.get(uri);

        // then
        ArgumentCaptor<HttpGet> argument = ArgumentCaptor.forClass(HttpGet.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getHeaders(HttpHeaders.SESSION_ID_KEY)[0].getValue(), is(sessionId));
        assertThat(argument.getValue().getHeaders(HttpHeaders.REQUEST_ID_KEY)[0].getValue(), is(requestId));
    }

    @Test
    public void shouldAddSessionIdAndRequestIdHeaderForPost() throws Exception {
        // when
        String sessionId = "someSessionId";
        String requestId = "someRequestId";
        ServiceMDC.setSessionId(sessionId);
        ServiceMDC.setRequestId(requestId);
        apiClient.post(uri, "Body");

        // then
        ArgumentCaptor<HttpPost> argument = ArgumentCaptor.forClass(HttpPost.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getHeaders(HttpHeaders.SESSION_ID_KEY)[0].getValue(), is(sessionId));
        assertThat(argument.getValue().getHeaders(HttpHeaders.REQUEST_ID_KEY)[0].getValue(), is(requestId));
    }

}