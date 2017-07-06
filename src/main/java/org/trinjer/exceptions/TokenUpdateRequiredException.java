package org.trinjer.exceptions;

public class TokenUpdateRequiredException extends Exception {

    public TokenUpdateRequiredException(String token) {
        super(token);
    }
}
