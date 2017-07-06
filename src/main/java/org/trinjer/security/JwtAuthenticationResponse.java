package org.trinjer.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse implements Serializable {
    private String jwtToken;
    private long timestamp;
    private Collection<String> permissions;
    private Integer userId;
}
