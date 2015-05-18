package com.gotocon.cdworkshop.exception;

import com.gotocon.cdworkshop.http.ApiHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiExceptionMapper implements ExceptionMapper {
    private final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionMapper.class);

    @Override
    public ServiceException mapErrorResponse(ApiHttpResponse httpResponse) {
        return ServiceException.TECHNICAL_ERROR;
    }
}
