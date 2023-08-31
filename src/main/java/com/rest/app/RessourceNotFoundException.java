package com.rest.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends  RuntimeException{

    private String message;

    public RessourceNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public  RessourceNotFoundException(){}

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
