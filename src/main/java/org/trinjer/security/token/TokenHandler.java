package org.trinjer.security.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.trinjer.exceptions.TokenExpirationException;
import org.trinjer.exceptions.TokenUpdateRequiredException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Component
public class TokenHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.renewtoken}")
    private String renewToken;
    @Value("${jwt.hashalgorithm}")
    private String hashAlgorithm;

    @Autowired
    private UserDetailsService userDetailsService;

    public String getTokenForUser(UserDetails userDetails) throws NoSuchAlgorithmException {
        StringBuilder tokenBuilder = new StringBuilder();
        long timestamp = System.currentTimeMillis() + getExpiration();
        StringBuilder authoritiesBuilder = new StringBuilder();
        userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(authoritiesBuilder::append);

        tokenBuilder
                .append(userDetails.getUsername()).append(":")
                .append(timestamp).append(":")
                .append(userDetails.getPassword()).append(":")
                .append(authoritiesBuilder.toString()).append(":")
                .append(secret);
        String tokenDigest = new String(Base64.encode(MessageDigest.getInstance(hashAlgorithm).digest(tokenBuilder.toString().getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        tokenBuilder = new StringBuilder();
        tokenBuilder
                .append(userDetails.getUsername()).append(":")
                .append(timestamp).append(":")
                .append(tokenDigest);
        return new String(Base64.encode(tokenBuilder.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public UserDetails getUserFromToken(String token) throws TokenExpirationException, NoSuchAlgorithmException, TokenUpdateRequiredException {
        String decoded = new String(Base64.decode(token.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String[] parsed = decoded.split(":");
        String email = parsed[0];
        long timestamp = Long.parseLong(parsed[1]);
        byte[] digest = Base64.decode(parsed[2].getBytes(StandardCharsets.UTF_8));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (timestamp < System.currentTimeMillis()) {
            throw new TokenExpirationException();
        } else if(timestamp - System.currentTimeMillis() < getRenewTokenPeriod()){
            logger.info("issue new token");
            throw new TokenUpdateRequiredException(getTokenForUser(userDetails));
        }
        StringBuilder authoritiesBuilder = new StringBuilder();
        userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(authoritiesBuilder::append);

        StringBuilder builder = new StringBuilder();
        builder
                .append(email).append(":")
                .append(timestamp).append(":")
                .append(userDetails.getPassword()).append(":")
                .append(authoritiesBuilder.toString()).append(":")
                .append(secret);
        byte[] expectedDigest = MessageDigest.getInstance(hashAlgorithm).digest(builder.toString().getBytes(StandardCharsets.UTF_8));
        if (Arrays.equals(expectedDigest, digest)) {
            return userDetails;
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    public long getTimestampFromToken(String token) {
        String decoded = new String(Base64.decode(token.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String[] parsed = decoded.split(":");
        return Long.parseLong(parsed[1]);
    }

    public long getExpiration() {
        return Long.parseLong(expiration) * 1000;
    }

    public long getRenewTokenPeriod() {
        return Long.parseLong(renewToken) * 1000;
    }

}
