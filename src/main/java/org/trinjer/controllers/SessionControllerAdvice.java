package org.trinjer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.trinjer.domain.UserNotFoundErrorInformation;
import org.trinjer.exceptions.UserExistException;

@RestControllerAdvice()
public class SessionControllerAdvice {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<UserNotFoundErrorInformation> userExistHandler(UserExistException ex) {
        UserNotFoundErrorInformation information = new UserNotFoundErrorInformation(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(information);
    }
}
