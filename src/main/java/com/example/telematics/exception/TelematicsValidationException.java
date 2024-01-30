package com.example.telematics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.core.Response;

public class TelematicsValidationException extends TelematicsException {

    public TelematicsValidationException(String message) {
        super(message);
    }

    public TelematicsValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Response.Status getHttpStatus() {
        return Response.Status.BAD_REQUEST;
    }

    @ResponseStatus
    public HttpStatus getHttpResponseStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
