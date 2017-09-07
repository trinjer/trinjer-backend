package org.trinjer.exceptions;

public class UserExistException extends Exception {
    public UserExistException(String email) {
        super("User with " + email + " already exist");
    }
}
