package org.trinjer.domain;

import org.trinjer.controllers.dto.UserDto;

public class UserNotFoundErrorInformation {
    private String errorMessage;

    public UserNotFoundErrorInformation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
