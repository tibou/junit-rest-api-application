package com.rest.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RessourceNotFoundException.class)
    public ResponseEntity ressourceNotFoundException(RessourceNotFoundException ressourceNotFoundException) {
        return new ResponseEntity(ressourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
