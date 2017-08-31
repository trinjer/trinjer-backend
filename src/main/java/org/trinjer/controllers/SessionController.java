package org.trinjer.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.trinjer.controllers.dto.UserDto;
import org.trinjer.controllers.dto.assemblers.DtoAssemblerService;
import org.trinjer.domain.UserEntity;
import org.trinjer.security.JwtAuthenticationRequest;
import org.trinjer.security.JwtAuthenticationResponse;
import org.trinjer.security.token.TokenHandler;
import org.trinjer.services.UserService;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@RestController
public class SessionController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenHandler tokenHandler;

    private final DtoAssemblerService dtoAssemblerService;

    @Autowired
    public SessionController(UserService userService, AuthenticationManager authenticationManager, TokenHandler tokenHandler, DtoAssemblerService dtoAssemblerService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.dtoAssemblerService = dtoAssemblerService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(dtoAssemblerService.assemble(userService.registerNewUser(userEntity)));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody JwtAuthenticationRequest request) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenHandler.getTokenForUser(userDetails);
        UserEntity gebruiker = userService.findByEmail(userDetails.getUsername());
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
