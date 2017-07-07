package org.trinjer.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinjer.controllers.dto.UserDto;
import org.trinjer.services.UserService;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@RestController
public class UserController extends AbstractApiController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity<Collection<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(dtoAssemblerService::assemble)
                .collect(Collectors.toList()));
    }
}
