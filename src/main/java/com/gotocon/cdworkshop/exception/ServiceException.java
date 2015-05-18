package com.gotocon.cdworkshop.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class ServiceException extends WebApplicationException{

    public static final String ERROR_MESSAGE = "{\"error\":\"A technical problem has occurred with the page you were trying to view. Please try again later.\"}";
    public static ServiceException TECHNICAL_ERROR = new ServiceException(BAD_REQUEST, ERROR_MESSAGE);

    private String message;

    public ServiceException(Status status, String errorMessage){
        super(Response.status(status).entity(errorMessage).type(MediaType.APPLICATION_JSON_TYPE).build());
        this.message = "Technical Error due to HTTP response status " + status.getStatusCode();
    }

    public static ServiceException fromException(Exception e) {
        return e instanceof ServiceException ? (ServiceException)e : TECHNICAL_ERROR;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
