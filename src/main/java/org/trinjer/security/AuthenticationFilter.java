package org.trinjer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.trinjer.exceptions.TokenExpirationException;
import org.trinjer.exceptions.TokenUpdateRequiredException;
import org.trinjer.security.token.TokenAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.trinjer.util.RestUtils.createJsonFromString;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            Authentication authentication = tokenAuthenticationService.getAuthentication(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            logger.warn(e.getMessage(), e);
            badCredentialsExceptionHandler(response, e);
        } catch (TokenExpirationException e) {
            logger.warn(e.getMessage(), e);
            tokenExpiredExceptionHandler(response, e);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            logger.warn(e.getMessage(), e);
            badTokenException(response);
        } catch (GeneralSecurityException e) {
            logger.warn(e.getMessage(), e);
            generalSecurityException(response, e);
        } catch (TokenUpdateRequiredException e) {
            logger.info(e.getMessage(), e);
            tokenUpdateRequiredExceptionHandler(response, e);
        }
    }

    private void generalSecurityException(ServletResponse response, GeneralSecurityException e) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        response.getWriter().write(e.getMessage());
    }

    private void badCredentialsExceptionHandler(ServletResponse response, Exception e) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(e.getMessage());
    }

    private void tokenExpiredExceptionHandler(ServletResponse response, Exception e) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(e.getMessage());
    }

    private void badTokenException(ServletResponse response) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("Bad token");
    }

    private void tokenUpdateRequiredExceptionHandler(ServletResponse response, Exception e) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        response.getWriter().write(createJsonFromString("jwtToken", e.getMessage()));
    }
}
