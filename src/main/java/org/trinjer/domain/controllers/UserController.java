package org.trinjer.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinjer.domain.UserEntity;
import org.trinjer.security.JwtAuthenticationRequest;
import org.trinjer.security.JwtAuthenticationResponse;
import org.trinjer.security.token.TokenHandler;
import org.trinjer.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenHandler tokenHandler;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity<Collection<UserEntity>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<UserEntity> registerNewUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.registerNewUser(userEntity));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody JwtAuthenticationRequest request) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenHandler.getTokenForUser(userDetails);
        UserEntity gebruiker = userService.findByUsername(userDetails.getUsername());
        JwtAuthenticationResponse response =
                new JwtAuthenticationResponse(
                        token,
                        tokenHandler.getTimestampFromToken(token),
                        Collections.emptyList(),
                        gebruiker.getId()
                );
        return ResponseEntity.ok(response);
    }
}
