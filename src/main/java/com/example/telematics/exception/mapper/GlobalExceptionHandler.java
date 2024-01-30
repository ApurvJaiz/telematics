package com.example.telematics.exception.mapper;

import com.example.telematics.exception.TelematicsApplicationException;
import com.example.telematics.exception.TelematicsException;
import com.example.telematics.exception.TelematicsValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TelematicsApplicationException.class)
    public ResponseEntity<String> handleTelematicsApplicationException(TelematicsApplicationException e) {
        log.error("Telematics Application Exception occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(TelematicsValidationException.class)
    public ResponseEntity<String> handleCustomException(TelematicsValidationException e) {
        log.error("Telematics Validation Exception occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}