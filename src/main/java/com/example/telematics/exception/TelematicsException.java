package com.example.telematics.exception;

import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;

public abstract class TelematicsException extends RuntimeException {
    protected TelematicsException(String message) {
        super(message);
    }

    protected TelematicsException(String message, Throwable cause) {
        super(message, cause);
    }


    public Response.Status getHttpStatus() {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    public abstract HttpStatus getHttpResponseStatus();

}
