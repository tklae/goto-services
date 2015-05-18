package com.gotocon.cdworkshop.exception;

import com.gotocon.cdworkshop.http.ApiHttpResponse;

public interface ExceptionMapper {
    public ServiceException mapErrorResponse(ApiHttpResponse httpResponse);
}
