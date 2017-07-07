package org.trinjer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.trinjer.controllers.dto.assemblers.DtoAssemblerService;

@RequestMapping("/api/v1")
public abstract class AbstractApiController {

    @Autowired
    protected DtoAssemblerService dtoAssemblerService;
}
