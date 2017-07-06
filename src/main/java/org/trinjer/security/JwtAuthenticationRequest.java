package org.trinjer.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
