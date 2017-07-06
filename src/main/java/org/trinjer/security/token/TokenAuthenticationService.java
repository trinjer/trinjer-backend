package org.trinjer.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.trinjer.exceptions.TokenExpirationException;
import org.trinjer.exceptions.TokenUpdateRequiredException;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Service
public class TokenAuthenticationService {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Autowired
    private TokenHandler tokenHandler;

    public Authentication getAuthentication(HttpServletRequest request) throws TokenExpirationException, NoSuchAlgorithmException, TokenUpdateRequiredException {
        String token = request.getHeader(jwtHeader);
        if (token != null) {
            UserDetails userDetails = tokenHandler.getUserFromToken(token);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                return authentication;
            }
        }
        return null;
    }
}
