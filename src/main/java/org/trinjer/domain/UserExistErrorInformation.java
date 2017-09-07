package org.trinjer.domain;

import org.trinjer.controllers.dto.UserDto;

public class UserExistErrorInformation {
    private String errorMessage;

    public UserExistErrorInformation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
