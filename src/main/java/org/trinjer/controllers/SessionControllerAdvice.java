package org.trinjer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.trinjer.domain.UserExistErrorInformation;
import org.trinjer.exceptions.UserExistException;

@RestControllerAdvice
public class SessionControllerAdvice {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<UserExistErrorInformation> userExistHandler(UserExistException ex) {
        UserExistErrorInformation information = new UserExistErrorInformation(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(information);
    }
}
