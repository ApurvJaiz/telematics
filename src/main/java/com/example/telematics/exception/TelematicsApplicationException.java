package com.example.telematics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.core.Response;

public class TelematicsApplicationException extends TelematicsException {

    public TelematicsApplicationException(String message) {
        super(message);
    }

    public TelematicsApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    @ResponseStatus
    public HttpStatus getHttpResponseStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public Response.Status getHttpStatus() {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }
}
