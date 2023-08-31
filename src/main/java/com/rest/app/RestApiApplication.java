package com.rest.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) throws RessourceNotFoundException{
        SpringApplication.run(RestApiApplication.class, args);
    }

}
