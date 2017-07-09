package org.trinjer.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationRequest implements Serializable {
    private String email;
    private String password;
}
