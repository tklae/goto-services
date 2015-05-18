package com.gotocon.cdworkshop.exception;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ServiceExceptionTest {

    @Test
    public void shouldConvertExceptionToTechnicalError() throws Exception {
        assertThat(ServiceException.fromException(new Exception("something")), is(ServiceException.TECHNICAL_ERROR));
    }

    @Test
    public void shouldKeepOriginalException() throws Exception {
        ServiceException exception = new ServiceException(Response.Status.CONFLICT, "someMessage");
        assertThat(ServiceException.fromException(exception), is(exception));
    }

}