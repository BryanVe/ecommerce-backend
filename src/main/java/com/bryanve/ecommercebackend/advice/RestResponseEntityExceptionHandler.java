package com.bryanve.ecommercebackend.advice;

import com.bryanve.ecommercebackend.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { ResponseStatusException.class })
    protected ResponseEntity<Object> handle(
            ResponseStatusException ex, WebRequest request) {
        String message = ex.getReason();
        int status = ex.getStatusCode().value();

        ErrorResponse response = new ErrorResponse(message, status);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, response.toString(),
                headers, ex.getStatusCode(), request);
    }

}