package org.trinjer.exceptions;

public class TokenExpirationException extends Exception {

    public TokenExpirationException() {
        super("Token has expired");
    }
}
